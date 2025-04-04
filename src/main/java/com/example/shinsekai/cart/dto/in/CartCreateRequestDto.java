package com.example.shinsekai.cart.dto.in;

import com.example.shinsekai.cart.entity.Cart;
import com.example.shinsekai.cart.vo.in.CartCreateRequestVo;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Getter
public class CartCreateRequestDto {
    private String memberUuid;
    private Long productOptionListId;
    private String productCode;
    private int quantity;
    private String engravingMessage;
    private boolean isFrozen;

    @Builder

    public CartCreateRequestDto(String memberUuid, Long productOptionListId, String productCode, int quantity,
                                String engravingMessage, boolean isFrozen) {
        this.memberUuid = memberUuid;
        this.productOptionListId = productOptionListId;
        this.productCode = productCode;
        this.quantity = quantity;
        this.engravingMessage = engravingMessage;
        this.isFrozen = isFrozen;
    }

    public static CartCreateRequestDto from(CartCreateRequestVo cartCreateRequestVo){
        return CartCreateRequestDto.builder()
                .memberUuid(cartCreateRequestVo.getMemberUuid()) // memberUUid header로 따로 받음?
                .productOptionListId(cartCreateRequestVo.getProductOptionListId())
                .productCode(cartCreateRequestVo.getProductCode())
                .quantity(cartCreateRequestVo.getQuantity())
                .engravingMessage(cartCreateRequestVo.getEngravingMessage())
                .isFrozen(cartCreateRequestVo.getIsFrozen())
                .build();
    }

    public Cart toEntity(){
        return Cart.builder()
                .cartUuid(generateCartCode())
                .memberUuid(memberUuid)
                .productOptionListId(productOptionListId)
                .productCode(productCode)
                .quantity(quantity)
                .engravingMessage(getCleanedEngravingMessage())
                .isFrozen(isFrozen)
                .build();
    }

    public String getCleanedEngravingMessage() {
        if (engravingMessage == null || engravingMessage.trim().isEmpty()) {
            return null;
        }
        return engravingMessage;
    }

    public static String generateCartCode() {
        String prefix = "C";
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String uuidPart = UUID.randomUUID().toString().substring(0, 8);

        return prefix + date + "-" + uuidPart;
    }
}
