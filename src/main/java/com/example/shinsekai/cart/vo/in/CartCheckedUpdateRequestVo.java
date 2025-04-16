package com.example.shinsekai.cart.vo.in;

import com.example.shinsekai.cart.entity.CartItemType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CartCheckedUpdateRequestVo {
    private CartItemType itemType;
    private Boolean checked;

    @Builder
    public CartCheckedUpdateRequestVo(CartItemType itemType, Boolean checked) {
        this.itemType = itemType;
        this.checked = checked;
    }
}
