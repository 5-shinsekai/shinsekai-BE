package com.example.shinsekai.cart.vo.out;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CartGetResponseVo {
    private String cartUuid;
    private String productCode;
    private Long productOptionListId;
    private int quantity;
    private boolean checked;
    private String engravingMessage;

    @Builder
    public CartGetResponseVo(String cartUuid, String productCode, Long productOptionListId,
                             int quantity, boolean checked, String engravingMessage) {
        this.cartUuid = cartUuid;
        this.productCode = productCode;
        this.productOptionListId = productOptionListId;
        this.quantity = quantity;
        this.checked = checked;
        this.engravingMessage = engravingMessage;
    }
}
