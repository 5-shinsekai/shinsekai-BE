package com.example.shinsekai.payment.dto.in;

import lombok.Getter;

@Getter
public class PaymentConfirmRequest {
    private String paymentKey;
    private String orderId;
    private Long amount;
}
