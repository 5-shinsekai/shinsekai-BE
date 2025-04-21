package com.example.shinsekai.cart.dto.out;

import com.example.shinsekai.cart.vo.out.CartGroupedByProductTypeVo;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class CartGroupedByProductTypeDto {
    List<CartGetResponseDto> ordinaryProducts;
    List<CartGetResponseDto> frozenProducts;

    @Builder
    public CartGroupedByProductTypeDto(List<CartGetResponseDto> ordinaryProducts,
                                       List<CartGetResponseDto> frozenProducts) {
        this.ordinaryProducts = ordinaryProducts;
        this.frozenProducts = frozenProducts;
    }

    public static CartGroupedByProductTypeDto ofGrouped(List<CartGetResponseDto> frozen,
                                                        List<CartGetResponseDto> ordinary) {
        return CartGroupedByProductTypeDto.builder()
                .frozenProducts(frozen)
                .ordinaryProducts(ordinary)
                .build();
    }

    public CartGroupedByProductTypeVo toVo() {
        return CartGroupedByProductTypeVo.builder()
                .ordinaryProducts(ordinaryProducts.stream()
                        .map(CartGetResponseDto::toVo).toList())
                .frozenProducts(frozenProducts.stream()
                        .map(CartGetResponseDto::toVo).toList())
                .build();
    }
}
