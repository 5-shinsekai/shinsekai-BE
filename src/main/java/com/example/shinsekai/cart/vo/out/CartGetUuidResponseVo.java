package com.example.shinsekai.cart.vo.out;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CartGetUuidResponseVo {
    private String cartUuid;

    @Builder
    public CartGetUuidResponseVo(String cartUuid) {
        this.cartUuid = cartUuid;
    }
}
