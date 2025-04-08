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
    public CartGroupedByProductTypeDto getAllCarts(String token) {
        String memberUuid;
        try {
            memberUuid = jwtTokenProvider.extractAllClaims(token).getSubject();
        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.TOKEN_NOT_VALID);
        }

        List<Cart> cartList = cartCustomRepository.findByMemberUuid(memberUuid);

        List<CartGetResponseDto> ordinary = new ArrayList<>();
        List<CartGetResponseDto> frozen = new ArrayList<>();

        for (Cart cart : cartList) {
            CartGetResponseDto dto = CartGetResponseDto.from(cart);
            if (cart.getIsFrozen()) {
                frozen.add(dto);
            } else {
                ordinary.add(dto);
            }
        }

        return CartGroupedByProductTypeDto.builder()
                .frozenProducts(frozen)
                .ordinaryProducts(ordinary)
                .build();
    }

    @Override
    @Transactional
    public void createCart(CartCreateRequestDto cartCreateRequestDto) {
        // 1. 유효한 상품 및 옵션인지 확인
        validateProductAndOption(cartCreateRequestDto);

        // 2. 장바구니 내 상품 총 수량 + 요청 수량이 제한을 초과하는지 확인
        validateQuantityLimit(cartCreateRequestDto);

        // 3. 동일한 상품+옵션+각인 항목이 있는지 확인
        Optional<Cart> optionalCart = cartCustomRepository.findCartByProductOptionAndEngraving(
                cartCreateRequestDto.getMemberUuid(), cartCreateRequestDto.getProductCode(),
                cartCreateRequestDto.getProductOptionListId(), cartCreateRequestDto.getEngravingMessage());

        if (optionalCart.isPresent()) {
            // 기존 항목 → 수량 증가
            Cart cart = optionalCart.get();
            cart.increaseQuantity(cartCreateRequestDto.getQuantity());
        } else {
            // 새로운 상품 추가 → 상품 종류 수 제한 검사 필요
            validateProductKindLimit(cartCreateRequestDto);

            try {
                cartRepository.save(cartCreateRequestDto.toEntity());
            } catch (Exception e) {
                throw new BaseException(BaseResponseStatus.FAILED_TO_RESTORE);
            }
        }
    }

    @Override
    @Transactional
    public void updateCart(String authorization, CartUpdateRequestDto cartUpdateRequestDto) {

    }

    @Override
    public void deleteCart(String token, List<CartDeleteRequestDto> cartDeleRequestDtoList) {

    }


    // 상품 코드, 옵션 유효성 검사
    private void validateProductAndOption(CartCreateRequestDto dto) {
        boolean isProductValid = productRepository.existsByProductCode(dto.getProductCode());
        boolean isOptionValid = productOptionListRepository.existsById(dto.getProductOptionListId());

        if (!isProductValid || !isOptionValid) {
            throw new BaseException(BaseResponseStatus.INVALID_INPUT);
        }
    }

    // 상품별 구매 갯수 확인
    private void validateQuantityLimit(CartCreateRequestDto dto) {
        int currentQuantity = cartCustomRepository.sumQuantityByProductCode(dto.getMemberUuid(), dto.getProductCode());
        int limitQuantity = productRepository.findByProductCode(dto.getProductCode())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_PRODUCT))
                .getUserPurchaseLimit();

        if (currentQuantity + dto.getQuantity() > limitQuantity) {
            throw new BaseException(BaseResponseStatus.CART_PRODUCT_QUANTITY_LIMIT_EXCEEDED);
        }
    }

    // 장바구니 상품 추가 가능여부 확인
    private void validateProductKindLimit(CartCreateRequestDto dto) {
        boolean isNewProductCode = !cartCustomRepository.existsByMemberUuidAndProductCode(
                                                                        dto.getMemberUuid(), dto.getProductCode());
        boolean isProductKindLimitExceeded = !cartCustomRepository.canAddMoreProductKindToCart(dto.getMemberUuid());

        if (isNewProductCode && isProductKindLimitExceeded) {
            throw new BaseException(BaseResponseStatus.CART_PRODUCT_KIND_LIMIT_EXCEEDED);
        }
    }

}
