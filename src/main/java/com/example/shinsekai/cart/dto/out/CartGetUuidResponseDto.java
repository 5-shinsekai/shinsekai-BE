package com.example.shinsekai.cart.dto.out;

import com.example.shinsekai.cart.entity.Cart;
import com.example.shinsekai.cart.vo.out.CartGetUuidResponseVo;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CartGetUuidResponseDto {
    private String cartUuid;

    @Builder
    public CartGetUuidResponseDto(String cartUuid) {
        this.cartUuid = cartUuid;
    }

    public static CartGetUuidResponseDto from(Cart cart) {
        return CartGetUuidResponseDto.builder()
                .cartUuid(cart.getCartUuid())
                .build();
    }

    public CartGetUuidResponseVo toVo() {
        return CartGetUuidResponseVo.builder()
                .cartUuid(cartUuid)
                .build();
    }
}