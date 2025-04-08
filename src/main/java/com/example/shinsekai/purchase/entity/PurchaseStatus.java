package com.example.shinsekai.purchase.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PurchaseStatus {
    PURCHASE_OK("주문 완료"),
    PURCHASE_CANCLE("주문 취소");

    private final String purchaseStatus;

    @JsonValue
    public String getPurchaseStatus() {
        return purchaseStatus;
    }

    @JsonCreator
    public static PurchaseStatus fromString(String value) {
        for(PurchaseStatus purchaseStatus : PurchaseStatus.values()) {
            if(purchaseStatus.name().equalsIgnoreCase(value)) {
                return purchaseStatus;
            }
        }
        throw new IllegalArgumentException("주문상태 오류: " + value);
    }
}
