package com.example.shinsekai.payment.application;

import com.example.shinsekai.payment.dto.in.PaymentRequestDto;
import com.example.shinsekai.payment.entity.Payment;
import com.example.shinsekai.purchase.dto.in.PurchaseTemporaryRequestDto;

public interface PaymentService {
    void createPayment(Payment payment);
    void deletePayment(PaymentRequestDto paymentRequestDto);
}
