package com.example.shinsekai.cart.application;

import com.example.shinsekai.cart.dto.in.CartCreateRequestDto;
import com.example.shinsekai.cart.dto.in.CartDeleteRequestDto;
import com.example.shinsekai.cart.dto.in.CartUpdateRequestDto;
import com.example.shinsekai.cart.dto.out.CartGetResponseDto;
import com.example.shinsekai.cart.dto.out.CartGroupedByProductTypeDto;
import com.example.shinsekai.cart.entity.Cart;
import com.example.shinsekai.cart.infrastructure.CartCustomRepoImpl;
import com.example.shinsekai.cart.infrastructure.CartRepository;
import com.example.shinsekai.common.entity.BaseResponseStatus;
import com.example.shinsekai.common.exception.BaseException;
import com.example.shinsekai.common.jwt.JwtTokenProvider;
import com.example.shinsekai.option.infrastructure.ProductOptionListRepository;
import com.example.shinsekai.product.infrastructure.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartCustomRepoImpl cartCustomRepository;
    private final ProductOptionListRepository productOptionListRepository;
    private final ProductRepository productRepository;

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    @Transactional
    public void createCart(CartCreateRequestDto cartCreateRequestDto) {
        // 1. 유효한 상품 및 옵션인지 확인
        if(!validateProductAndOption(cartCreateRequestDto))
            throw new BaseException(BaseResponseStatus.INVALID_INPUT);

        // 2. 장바구니에 동일한 상품이 있는지 조회
        List<Cart> carts = cartCustomRepository.findAllCartByProduct(
                cartCreateRequestDto.getMemberUuid(),
                cartCreateRequestDto.getProductCode()
        );

        // 3. 동일한 상품이 없으면
        if (carts.isEmpty()) {
            // 장바구니 상품 최대 20개
            if (!validateProductKindLimit(cartCreateRequestDto))
                throw new BaseException(BaseResponseStatus.CART_PRODUCT_KIND_LIMIT_EXCEEDED);
            cartRepository.save(cartCreateRequestDto.toEntity());
        } else {
            // 4. 동일한 상품이 있으면 상품별 수량 확인
            if(!validateQuantityLimit(carts, cartCreateRequestDto))
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
        List<Cart> cartList = cartCustomRepository.findByMemberUuid(memberUuid);

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
        cartRepository.findByMemberUuidAndId(memberUuid, cartDeleteRequestDto.getId()).orElseThrow(
                () -> new BaseException(BaseResponseStatus.INVALID_CART_ACCESS)
        ).setDeleted();
    }

    @Override
    @Transactional
    public void deleteSelectedAllCart(String memberUuid, List<CartDeleteRequestDto> cartDeleRequestDtoList) {
        List<Cart> carts = cartRepository.findAllByMemberUuidAndIdIn(memberUuid,
                cartDeleRequestDtoList.stream()
                        .map(CartDeleteRequestDto::getId)
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
    public void updateCart(String authorization, CartUpdateRequestDto cartUpdateRequestDto) {

    }

    // 상품 코드, 옵션 유효성 검사
    private boolean validateProductAndOption(CartCreateRequestDto dto) {
        if (!productRepository.existsByProductCode(dto.getProductCode()))
            return false;

        if (!productOptionListRepository.existsById(dto.getProductOptionListId()))
            return false;

        return true;
    }

    // 상품별 구매 갯수 확인
    private boolean validateQuantityLimit(List<Cart> cartList, CartCreateRequestDto dto) {
        int carttTotalQuantity = cartList.stream()
                .mapToInt(Cart::getQuantity)
                .sum();
        int limitQuantity = productRepository.findByProductCode(dto.getProductCode())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_PRODUCT))
                .getUserPurchaseLimit();

        return carttTotalQuantity + dto.getQuantity() <= limitQuantity;
    }

    // 장바구니 상품 추가 가능여부 확인
    private boolean validateProductKindLimit(CartCreateRequestDto dto) {
        return cartCustomRepository.canAddMoreProductKindToCart(dto.getMemberUuid());
    }

}
