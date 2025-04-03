package com.example.shinsekai.payment.application;

import com.example.shinsekai.payment.dto.in.PaymentRequestDto;

public interface PaymentService {
    String handlePaymentSuccess(String purchaseId, String paymentKey, int amount);
    void createPayment(PaymentRequestDto paymentRequestDto);
}
