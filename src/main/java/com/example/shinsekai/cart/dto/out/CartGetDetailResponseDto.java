package com.example.shinsekai.cart.dto.out;

import com.example.shinsekai.cart.entity.Cart;
import com.example.shinsekai.cart.vo.out.CartGetDetailResponseVo;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CartGetDetailResponseDto {
    private String productCode;
    private Long productOptionListId;
    private int quantity;
    private boolean checked;
    private String engravingMessage;

    @Builder
    public CartGetDetailResponseDto(String productCode, Long productOptionListId,
                                    int quantity, boolean checked, String engravingMessage) {
        this.productCode = productCode;
        this.productOptionListId = productOptionListId;
        this.quantity = quantity;
        this.checked = checked;
        this.engravingMessage = engravingMessage;
    }

    public static CartGetDetailResponseDto from(Cart cart) {
        return CartGetDetailResponseDto.builder()
                .productCode(cart.getProductCode())
                .productOptionListId(cart.getProductOptionListId())
                .quantity(cart.getQuantity())
                .checked(cart.getChecked())
                .engravingMessage(cart.getEngravingMessage())
                .build();
    }

    public CartGetDetailResponseVo toVo() {
        return CartGetDetailResponseVo.builder()
                .productCode(productCode)
                .productOptionListId(productOptionListId)
                .quantity(quantity)
                .checked(checked)
                .engravingMessage(engravingMessage)
                .build();
    }
}