package com.example.shinsekai.cart.dto.in;

import com.example.shinsekai.cart.entity.CartItemType;
import com.example.shinsekai.cart.vo.in.CartCheckedUpdateRequestVo;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CartCheckedUpdateRequestDto {
    private String memberUuid;
    private CartItemType itemType;
    private Boolean checked;

    @Builder
    public CartCheckedUpdateRequestDto(String memberUuid, CartItemType itemType, Boolean checked) {
        this.memberUuid = memberUuid;
        this.itemType = itemType;
        this.checked = checked;
    }

    public static CartCheckedUpdateRequestDto from(String memberUuid, CartCheckedUpdateRequestVo vo) {
        return CartCheckedUpdateRequestDto.builder()
                .memberUuid(memberUuid)
                .itemType(vo.getItemType())
                .checked(vo.getChecked())
                .build();
    }


}
