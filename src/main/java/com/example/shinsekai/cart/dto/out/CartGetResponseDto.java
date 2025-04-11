package com.example.shinsekai.cart.dto.out;

import com.example.shinsekai.cart.entity.Cart;
import com.example.shinsekai.cart.vo.out.CartGetResponseVo;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CartGetResponseDto {
    private String cartUuid;
    private String productCode;
    private Long productOptionListId;
    private int quantity;
    private boolean checked;
    private String engravingMessage;

    @Builder
    public CartGetResponseDto(String cartUuid, String productCode, Long productOptionListId,
                              int quantity, boolean checked, String engravingMessage) {
        this.cartUuid = cartUuid;
        this.productCode = productCode;
        this.productOptionListId = productOptionListId;
        this.quantity = quantity;
        this.checked = checked;
        this.engravingMessage = engravingMessage;
    }

    public static CartGetResponseDto from(Cart cart) {
        return CartGetResponseDto.builder()
                .cartUuid(cart.getCartUuid())
                .productCode(cart.getProductCode())
                .productOptionListId(cart.getProductOptionListId())
                .quantity(cart.getQuantity())
                .checked(cart.getChecked())
                .engravingMessage(cart.getEngravingMessage())
                .build();
    }

    public CartGetResponseVo toVo(){
        return CartGetResponseVo.builder()
                .cartUuid(cartUuid)
                .productCode(productCode)
                .productOptionListId(productOptionListId)
                .quantity(quantity)
                .checked(checked)
                .engravingMessage(engravingMessage)
                .build();
    }
}