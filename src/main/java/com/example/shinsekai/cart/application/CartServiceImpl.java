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
        // 1. 유효한 상품 및 옵션인지 확인 (return 추가)
        if(!validateProductAndOption(cartCreateRequestDto))
            throw new BaseException(BaseResponseStatus.INVALID_INPUT);

        // 2. 장바구니에 동일한 상품이 있는지 조회
        cartCustomRepository.findCartByProductAndOption(
                cartCreateRequestDto.getMemberUuid(),
                cartCreateRequestDto.getProductCode(),
                cartCreateRequestDto.getProductOptionListId()
        ).ifPresentOrElse(
                cart -> {
                    if (!validateQuantityLimit(cartCreateRequestDto)) {
                        throw new BaseException(BaseResponseStatus.CART_PRODUCT_QUANTITY_LIMIT_EXCEEDED);
                    }
                    // 이미 존재 → 수량 증가
                    cart.increaseQuantity(cartCreateRequestDto.getQuantity());
                },
                () -> {
                    // 존재하지 않음 → 수량 종류 제한 검증 후 새로 저장
                    if(!validateProductKindLimit(cartCreateRequestDto))
                        throw new BaseException(BaseResponseStatus.CART_PRODUCT_KIND_LIMIT_EXCEEDED);
                    cartRepository.save(cartCreateRequestDto.toEntity());
                }
        );
    }

    @Override
    @Transactional
    public CartGroupedByProductTypeDto getAllCarts(String token) {
        String memberUuid;
        try {
            memberUuid = jwtTokenProvider.extractAllClaims(token).getSubject();
        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.TOKEN_NOT_VALID);
        }

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
        if(!productRepository.existsByProductCode(dto.getProductCode()))
            return false;

        if(!productOptionListRepository.existsById(dto.getProductOptionListId()))
            return false;

        return true;
    }

    // 상품별 구매 갯수 확인
    private boolean validateQuantityLimit(CartCreateRequestDto dto) { // true/false로 return 하여 처리
        int currentQuantity = cartCustomRepository.sumQuantityByProductCode(dto.getMemberUuid(), dto.getProductCode());
        int limitQuantity = productRepository.findByProductCode(dto.getProductCode())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_PRODUCT))
                .getUserPurchaseLimit();

        return currentQuantity + dto.getQuantity() <= limitQuantity;
    }

    // 장바구니 상품 추가 가능여부 확인
    private boolean validateProductKindLimit(CartCreateRequestDto dto) {
        boolean isNewProductCode = !cartCustomRepository.existsByMemberUuidAndProductCode(
                                                                        dto.getMemberUuid(), dto.getProductCode());
        boolean isProductKindLimitExceeded = !cartCustomRepository.canAddMoreProductKindToCart(dto.getMemberUuid());

        return !isNewProductCode || !isProductKindLimitExceeded;
    }

}
