package com.example.shinsekai.purchase.dto.in;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseDeleteRequestDto {
    private String purchaseCode;
    private String memberUuid;

    public static PurchaseDeleteRequestDto from(String purchaseCode, String memberUuid) {
        return PurchaseDeleteRequestDto.builder().purchaseCode(purchaseCode).memberUuid(memberUuid).build();
    }
}
