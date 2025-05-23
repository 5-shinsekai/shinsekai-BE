package com.example.shinsekai.cart.application;

import com.example.shinsekai.cart.dto.in.CartCheckedUpdateRequestDto;
import com.example.shinsekai.cart.dto.in.CartCreateRequestDto;
import com.example.shinsekai.cart.dto.in.CartDeleteRequestDto;
import com.example.shinsekai.cart.dto.in.CartUpdateRequestDto;
import com.example.shinsekai.cart.dto.out.CartGetResponseDto;
import com.example.shinsekai.cart.dto.out.CartGetUuidResponseDto;
import com.example.shinsekai.cart.dto.out.CartGroupedByProductTypeDto;
import com.example.shinsekai.cart.dto.out.CartUuidGroupedByProductTypeDto;
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

    private static final int MAX_CART_PRODUCT_KINDS_PER_USER = 20;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public void createCart(CartCreateRequestDto cartCreateRequestDto) {
        List<Cart> carts = cartRepository.findAllByMemberUuidAndProductCodeAndIsDeletedFalse(
                cartCreateRequestDto.getMemberUuid(),
                cartCreateRequestDto.getProductCode()
        );

        if (carts.isEmpty()) {
            // 장바구니 상품 최대 20개
            if (!validateProductKindLimit(cartCreateRequestDto.getMemberUuid()))
                throw new BaseException(BaseResponseStatus.CART_PRODUCT_KIND_LIMIT_EXCEEDED);
            cartRepository.save(cartCreateRequestDto.toEntity());
        } else {
            carts.stream()
                    .filter(cart -> {
                        boolean sameProduct = cart.getProductCode().equals(cartCreateRequestDto.getProductCode());

                        // 옵션이 없는 상품: 서로 optionId가 null일 때만 같은 상품으로 간주
                        if (cartCreateRequestDto.getProductOptionListId() == null) {
                            return sameProduct && cart.getProductOptionListId() == null;
                        }

                        // 옵션이 있는 상품: optionId도 동일해야 같은 상품
                        return sameProduct &&
                                cartCreateRequestDto.getProductOptionListId().equals(cart.getProductOptionListId());
                    })
                    .findFirst()
                    .ifPresentOrElse(
                            cart -> {
                                if (!validateQuantityLimit(cart, cartCreateRequestDto.getProductCode(),
                                        cartCreateRequestDto.getQuantity())) {
                                    throw new BaseException(BaseResponseStatus.CART_PRODUCT_QUANTITY_LIMIT_EXCEEDED);
                                }

                                // 기존에 같은 상품이 있으면 수량만 증가
                                cart.increaseQuantity(cartCreateRequestDto.getQuantity());
                            },
                            () -> {
                                if (!validateQuantityLimit(null, cartCreateRequestDto.getProductCode(),
                                        cartCreateRequestDto.getQuantity())) {
                                    throw new BaseException(BaseResponseStatus.CART_PRODUCT_QUANTITY_LIMIT_EXCEEDED);
                                }
                                // 없으면 새로 추가
                                cartRepository.save(cartCreateRequestDto.toEntity());
                            }
                    );
        }
    }

    @Override
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

        return CartGroupedByProductTypeDto.ofGrouped(frozen, ordinary);
    }

    @Override
    public CartUuidGroupedByProductTypeDto getAllCartsUuid(String memberUuid) {
        List<Cart> cartList = cartRepository.findAllByMemberUuidAndIsDeletedFalse(memberUuid);

        List<CartGetUuidResponseDto> frozen = cartList.stream()
                .filter(Cart::getIsFrozen)
                .map(CartGetUuidResponseDto::from)
                .toList();

        List<CartGetUuidResponseDto> ordinary = cartList.stream()
                .filter(cart -> !cart.getIsFrozen())
                .map(CartGetUuidResponseDto::from)
                .toList();

        return CartUuidGroupedByProductTypeDto.ofGrouped(frozen, ordinary);
    }

    @Override
    public CartGetResponseDto getAllCartsDetail(String cartUuid) {
        return CartGetResponseDto.from(
                cartRepository.findByCartUuidAndIsDeletedFalse(cartUuid).orElseThrow(
                        () -> new BaseException(BaseResponseStatus.INVALID_CART_ACCESS))
        );
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

        return CartGroupedByProductTypeDto.ofGrouped(frozen, ordinary);
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
                cartUpdateRequestDto.getMemberUuid(),
                cartUpdateRequestDto.getCartUuid()
        ).orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_CART_ACCESS));

        // 같은 상품의 장바구니 목록 조회 (isDeleted = false)
        List<Cart> sameProductCarts = cartRepository.findAllByMemberUuidAndProductCodeAndIsDeletedFalse(
                cartUpdateRequestDto.getMemberUuid(),
                cart.getProductCode()
        );

        // 수정 중인 cart는 제외하고, 옵션 중복 여부 확인
        boolean isDuplicateOption = sameProductCarts.stream()
                .filter(c -> !c.getId().equals(cart.getId()))
                .anyMatch(c -> Objects.equals(
                        c.getProductOptionListId(),
                        cartUpdateRequestDto.getProductOptionListId()
                ));

        if (isDuplicateOption) {
            throw new BaseException(BaseResponseStatus.DUPLICATE_CART_OPTION);
        }


        if (cartUpdateRequestDto.getQuantity() != null) {
            if (!validateQuantityLimit(null, cart.getProductCode(), cartUpdateRequestDto.getQuantity())) {
                throw new BaseException(BaseResponseStatus.CART_PRODUCT_QUANTITY_LIMIT_EXCEEDED);
            }
        }

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

    private boolean validateQuantityLimit(Cart cart, String productCode, int quantity) {
        int cartTotalQuantity = cart != null ? cart.getQuantity() : 0;
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
