package com.example.shinsekai.cart.dto.out;

import com.example.shinsekai.cart.entity.Cart;
import com.example.shinsekai.cart.vo.out.CartGetResponseVo;
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

    public CartGroupedByProductTypeVo toVo(){
        return CartGroupedByProductTypeVo.builder()
                .ordinaryProducts(ordinaryProducts.stream()
                        .map(CartGetResponseDto::toVo).toList())
                .frozenProducts(frozenProducts.stream()
                        .map(CartGetResponseDto::toVo).toList())
                .build();
    }
}
