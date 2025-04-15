package com.example.shinsekai.cart.dto.in;

import com.example.shinsekai.cart.entity.Cart;
import com.example.shinsekai.cart.vo.in.CartUpdateRequestVo;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CartUpdateRequestDto {
    private String memberUuid;
    private String cartUuid;
    private Integer quantity;
    private Boolean checked;
    private Long productOptionListId;

    @Builder
    public CartUpdateRequestDto(String memberUuid, String cartUuid,
                                Integer quantity, Boolean checked, Long productOptionListId) {
        this.memberUuid = memberUuid;
        this.cartUuid = cartUuid;
        this.quantity = quantity;
        this.checked = checked;
        this.productOptionListId = productOptionListId;
    }

    public static CartUpdateRequestDto from(String memberUuid, CartUpdateRequestVo vo){
        return CartUpdateRequestDto.builder()
                .memberUuid(memberUuid)
                .cartUuid(vo.getCartUuid())
                .quantity(vo.getQuantity())
                .checked(vo.getChecked())
                .productOptionListId(vo.getProductOptionListId())
                .build();
    }

    public Cart toEntity(Cart cart){
        return Cart.builder()
                .id(cart.getId())
                .cartUuid(cartUuid)
                .memberUuid(memberUuid)
                .productOptionListId(productOptionListId == null ? cart.getProductOptionListId() : productOptionListId)
                .productCode(cart.getProductCode())
                .quantity(quantity == null ? cart.getQuantity() : quantity)
                .checked(checked == null ? cart.getChecked() : checked)
                .engravingMessage(cart.getEngravingMessage())
                .isFrozen(cart.getIsFrozen())
                .isDeleted(cart.getIsDeleted())
                .build();
    }
}
