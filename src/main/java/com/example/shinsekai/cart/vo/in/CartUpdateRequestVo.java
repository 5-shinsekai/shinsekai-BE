package com.example.shinsekai.cart.vo.in;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CartUpdateRequestVo {
    @NotNull
    private String cartUuid;
    private Integer quantity;
    private Boolean checked;
    private Long productOptionListId;

    @Builder
    public CartUpdateRequestVo(String cartUuid, Integer quantity, Boolean checked, Long productOptionListId) {
        this.cartUuid = cartUuid;
        this.quantity = quantity;
        this.checked = checked;
        this.productOptionListId = productOptionListId;
    }
}
