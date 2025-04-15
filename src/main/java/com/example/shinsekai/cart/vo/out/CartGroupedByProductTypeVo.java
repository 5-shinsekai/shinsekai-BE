package com.example.shinsekai.cart.vo.out;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class CartGroupedByProductTypeVo {
    private List<CartGetResponseVo> ordinaryProducts;
    private List<CartGetResponseVo> frozenProducts;

    @Builder
    public CartGroupedByProductTypeVo(List<CartGetResponseVo> ordinaryProducts,
                                      List<CartGetResponseVo> frozenProducts) {
        this.ordinaryProducts = ordinaryProducts;
        this.frozenProducts = frozenProducts;
    }
}
