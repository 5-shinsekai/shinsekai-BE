package com.example.shinsekai.cart.application;

import com.example.shinsekai.cart.dto.in.CartCheckedUpdateRequestDto;
import com.example.shinsekai.cart.dto.in.CartCreateRequestDto;
import com.example.shinsekai.cart.dto.in.CartDeleteRequestDto;
import com.example.shinsekai.cart.dto.in.CartUpdateRequestDto;
import com.example.shinsekai.cart.dto.out.CartGetResponseDto;
import com.example.shinsekai.cart.dto.out.CartGroupedByProductTypeDto;
import com.example.shinsekai.cart.entity.Cart;
import com.example.shinsekai.cart.infrastructure.CartRepository;
import com.example.shinsekai.common.entity.BaseResponseStatus;
import com.example.shinsekai.common.exception.BaseException;
import com.example.shinsekai.product.infrastructure.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    private static final int MAX_CART_PRODUCT_KINDS_PER_USER = 20;

    @Override
    @Transactional
    public void createCart(CartCreateRequestDto cartCreateRequestDto) {
        // 1. 장바구니에 동일한 상품이 있는지 조회
        List<Cart> carts = cartRepository.findAllByMemberUuidAndProductCodeAndIsDeletedFalse(
                cartCreateRequestDto.getMemberUuid(),
                cartCreateRequestDto.getProductCode()
        );

        // 2. 동일한 상품이 없으면
        if (carts.isEmpty()) {
            // 장바구니 상품 최대 20개
            if (!validateProductKindLimit(cartCreateRequestDto.getMemberUuid()))
                throw new BaseException(BaseResponseStatus.CART_PRODUCT_KIND_LIMIT_EXCEEDED);
            cartRepository.save(cartCreateRequestDto.toEntity());
        } else {
            // 3. 동일한 상품이 있으면 상품별 수량 확인
            if (!validateQuantityLimit(carts, cartCreateRequestDto.getProductCode(), cartCreateRequestDto.getQuantity()))
                throw new BaseException(BaseResponseStatus.CART_PRODUCT_QUANTITY_LIMIT_EXCEEDED);

            carts.stream()
                    .filter(c -> c.getProductOptionListId().equals(cartCreateRequestDto.getProductOptionListId()))
                    .findFirst()
                    .ifPresentOrElse(
                            cart -> {
                                cart.increaseQuantity(cartCreateRequestDto.getQuantity());
                            },
                            () -> {
                                cartRepository.save(cartCreateRequestDto.toEntity());
                            });
        }
    }

    @Override
    @Transactional
    public CartGroupedByProductTypeDto getAllCarts(String memberUuid) {
        List<Cart> cartList = cartRepository.findAllByMemberUuidAndIsDeletedFalse(memberUuid);

        List<CartGetResponseDto> frozen = cartList.stream()
                .filter(Cart::getIsFrozen)
                .map(CartGetResponseDto::from)
                .toList();

        List<CartGetResponseDto> ordinary = cartList.stream()
                .filter(cart -> !cart.getIsFrozen())
                .map(CartGetResponseDto::from)
                .toList();

        return CartGroupedByProductTypeDto.builder()
                .frozenProducts(frozen)
                .ordinaryProducts(ordinary)
                .build();
    }

    @Override
    public CartGroupedByProductTypeDto getAllCheckedCarts(String memberUuid) {
        List<Cart> cartList = cartRepository.findAllByMemberUuidAndCheckedTrueAndIsDeletedFalse(memberUuid);

        List<CartGetResponseDto> frozen = cartList.stream()
                .filter(Cart::getIsFrozen)
                .map(CartGetResponseDto::from)
                .toList();

        List<CartGetResponseDto> ordinary = cartList.stream()
                .filter(cart -> !cart.getIsFrozen())
                .map(CartGetResponseDto::from)
                .toList();

        return CartGroupedByProductTypeDto.builder()
                .frozenProducts(frozen)
                .ordinaryProducts(ordinary)
                .build();
    }

    @Override
    @Transactional
    public void deleteCart(String memberUuid, CartDeleteRequestDto cartDeleteRequestDto) {
        cartRepository.findByMemberUuidAndCartUuidAndIsDeletedFalse(memberUuid, cartDeleteRequestDto.getCartUuid()).orElseThrow(
                () -> new BaseException(BaseResponseStatus.INVALID_CART_ACCESS)
        ).setDeleted();
    }

    @Override
    @Transactional
    public void deleteSelectedAllCart(String memberUuid, List<CartDeleteRequestDto> cartDeleRequestDtoList) {
        List<Cart> carts = cartRepository.findAllByMemberUuidAndCartUuidInAndIsDeletedFalse(memberUuid,
                cartDeleRequestDtoList.stream()
                        .map(CartDeleteRequestDto::getCartUuid)
                        .toList());

        if (carts.size() != cartDeleRequestDtoList.size()) {
            throw new BaseException(BaseResponseStatus.INVALID_CART_ACCESS);
        }

        carts.forEach(Cart::setDeleted);
    }

    @Override
    @Transactional
    public void deleteAllCart(String memberUuid) {
        cartRepository.softDeleteAllByMemberUuid(memberUuid);
    }

    @Override
    @Transactional
    public void updateCart(CartUpdateRequestDto cartUpdateRequestDto) {
        Cart cart = cartRepository.findByMemberUuidAndCartUuidAndIsDeletedFalse(
                cartUpdateRequestDto.getMemberUuid(), cartUpdateRequestDto.getCartUuid()).orElseThrow(
                () -> new BaseException(BaseResponseStatus.INVALID_CART_ACCESS)
        );


        List<Cart> cartList = cartRepository.findAllByMemberUuidAndProductCodeAndIsDeletedFalse(
                cartUpdateRequestDto.getMemberUuid(),
                cart.getProductCode());

        if (cartUpdateRequestDto.getProductOptionListId() != null) {
            cartList.stream()
                    .filter(c -> Objects.equals(c.getProductOptionListId(),
                            cartUpdateRequestDto.getProductOptionListId()))
                    .findFirst()
                    .ifPresent(existingCart -> {
                        throw new BaseException(BaseResponseStatus.DUPLICATE_CART_OPTION);
                    });
        }

        // 4. 동일한 상품이 있으면 상품별 수량 확인
        if (!validateQuantityLimit(cartList, cart.getProductCode(), cartUpdateRequestDto.getQuantity()))
            throw new BaseException(BaseResponseStatus.CART_PRODUCT_QUANTITY_LIMIT_EXCEEDED);

        cartRepository.save(cartUpdateRequestDto.toEntity(cart));
    }

    @Override
    @Transactional
    public void updateAllCartChecked(CartCheckedUpdateRequestDto cartCheckedUpdateRequestDto) {
        Boolean isFrozen = switch (cartCheckedUpdateRequestDto.getItemType()) {
            case ALL -> null;
            case ORDINARY -> false;
            case FROZEN -> true;
        };

        cartRepository.updateCheckedStatusByType(cartCheckedUpdateRequestDto.getMemberUuid(),
                cartCheckedUpdateRequestDto.getChecked(),
                isFrozen);
    }

    // 상품별 구매 갯수 확인
    private boolean validateQuantityLimit(List<Cart> cartList, String productCode, int quantity) {
        int cartTotalQuantity = cartList.stream()
                .mapToInt(Cart::getQuantity)
                .sum();
        int limitQuantity = productRepository.findByProductCode(productCode)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_PRODUCT))
                .getUserPurchaseLimit();

        return cartTotalQuantity + quantity <= limitQuantity;
    }

    // 장바구니 상품 추가 가능여부 확인
    private boolean validateProductKindLimit(String memberUuid) {
        Long count = cartRepository.countDistinctProductCodeByMemberUuid(memberUuid);
        return count == null || count < MAX_CART_PRODUCT_KINDS_PER_USER;
    }

}
