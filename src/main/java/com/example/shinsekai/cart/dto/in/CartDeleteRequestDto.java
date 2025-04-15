package com.example.shinsekai.cart.dto.in;

import com.example.shinsekai.cart.vo.in.CartDeleteRequestVo;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CartDeleteRequestDto {
    private String cartUuid;

    @Builder
    public CartDeleteRequestDto(String cartUuid) {
        this.cartUuid = cartUuid;
    }

    public static CartDeleteRequestDto from(CartDeleteRequestVo cartDeleteRequestVo) {
        return CartDeleteRequestDto.builder()
                .cartUuid(cartDeleteRequestVo.getCartUuid())
                .build();
    }

    public static CartDeleteRequestDto from(String cartUuid) {
        return CartDeleteRequestDto.builder()
                .cartUuid(cartUuid)
                .build();
    }
}
