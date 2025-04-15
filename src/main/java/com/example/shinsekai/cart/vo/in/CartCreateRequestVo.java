package com.example.shinsekai.cart.vo.in;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class CartCreateRequestVo {
    private Long productOptionListId;
    private String productCode;
    private int quantity;
    @Pattern(
            regexp = "^[A-Z0-9 ]{1,10}$|^[가-힣0-9 ]{1,5}$",
            message = "각인 문구는 영문 대문자 최대 10자 또는 한글 최대 5자만 입력할 수 있으며, 특수문자는 사용할 수 없습니다."
    )
    private String engravingMessage;
    private boolean isFrozen;

    public boolean getIsFrozen() {
        return isFrozen;
    }
}
