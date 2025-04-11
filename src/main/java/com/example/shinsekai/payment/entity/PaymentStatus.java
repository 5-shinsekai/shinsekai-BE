package com.example.shinsekai.payment.entity;

import com.example.shinsekai.purchase.entity.PurchaseStatus;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentStatus {
    PAYMENT_OK("DONE"),
    PAYMENT_CANCEL("CANCEL"),;

    private final String paymentStatus;

    @JsonValue
    public String getPaymentStatus() {
        return paymentStatus;
    }

    @JsonCreator
    public static PaymentStatus fromString(String value) {
        for(PaymentStatus paymentStatus : PaymentStatus.values()) {
            if(paymentStatus.name().equalsIgnoreCase(value)) {
                return paymentStatus;
            }
        }
        throw new IllegalArgumentException("결제상태 오류: " + value);
    }

}
