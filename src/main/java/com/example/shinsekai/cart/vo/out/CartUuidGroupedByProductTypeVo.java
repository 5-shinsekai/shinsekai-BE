package com.example.shinsekai.cart.vo.out;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class CartUuidGroupedByProductTypeVo {
    private List<CartGetUuidResponseVo> ordinaryProducts;
    private List<CartGetUuidResponseVo> frozenProducts;

    @Builder

    public CartUuidGroupedByProductTypeVo(List<CartGetUuidResponseVo> ordinaryProducts,
                                          List<CartGetUuidResponseVo> frozenProducts) {
        this.ordinaryProducts = ordinaryProducts;
        this.frozenProducts = frozenProducts;
    }
}
