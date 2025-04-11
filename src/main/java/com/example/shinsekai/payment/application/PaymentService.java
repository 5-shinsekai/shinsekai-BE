package com.example.shinsekai.payment.application;

import com.example.shinsekai.payment.dto.in.PaymentDeleteRequestDto;
import com.example.shinsekai.payment.dto.in.PaymentRequestDto;

public interface PaymentService {
    void createPayment(PaymentRequestDto paymentRequestDto);
    void deletePayment(PaymentDeleteRequestDto paymentDeleteRequestDto);
}
