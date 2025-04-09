package com.example.shinsekai.cart.dto.in;

import com.example.shinsekai.cart.vo.in.CartDeleteRequestVo;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CartDeleteRequestDto {
    private Long id;

    @Builder
    public CartDeleteRequestDto(Long id) {
        this.id = id;
    }

    public static CartDeleteRequestDto from(CartDeleteRequestVo cartDeleteRequestVo) {
        return CartDeleteRequestDto.builder()
                .id(cartDeleteRequestVo.getId())
                .build();
    }

    public static CartDeleteRequestDto from(Long id) {
        return CartDeleteRequestDto.builder()
                .id(id)
                .build();
    }
}
