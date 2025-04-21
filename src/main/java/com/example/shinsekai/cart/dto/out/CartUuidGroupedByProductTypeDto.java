package com.example.shinsekai.cart.dto.out;

import com.example.shinsekai.cart.vo.out.CartUuidGroupedByProductTypeVo;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class CartUuidGroupedByProductTypeDto {
    List<CartGetUuidResponseDto> ordinaryProducts;
    List<CartGetUuidResponseDto> frozenProducts;

    @Builder
    public CartUuidGroupedByProductTypeDto(List<CartGetUuidResponseDto> ordinaryProducts,
                                           List<CartGetUuidResponseDto> frozenProducts) {
        this.ordinaryProducts = ordinaryProducts;
        this.frozenProducts = frozenProducts;
    }

    public static CartUuidGroupedByProductTypeDto ofGrouped(List<CartGetUuidResponseDto> frozen,
                                                            List<CartGetUuidResponseDto> ordinary) {
        return CartUuidGroupedByProductTypeDto.builder()
                .frozenProducts(frozen)
                .ordinaryProducts(ordinary)
                .build();
    }

    public CartUuidGroupedByProductTypeVo toVo() {
        return CartUuidGroupedByProductTypeVo.builder()
                .ordinaryProducts(ordinaryProducts.stream()
                        .map(CartGetUuidResponseDto::toVo).toList())
                .frozenProducts(frozenProducts.stream()
                        .map(CartGetUuidResponseDto::toVo).toList())
                .build();
    }

}
