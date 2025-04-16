package com.example.shinsekai.payment.application;

import com.example.shinsekai.payment.dto.in.PaymentDeleteRequestDto;
import com.example.shinsekai.payment.dto.in.PaymentRequestDto;
import com.example.shinsekai.payment.dto.out.PaymentResponseDto;

public interface PaymentService {
    PaymentResponseDto findAllPayment(String paymentCode, String memberUuid);
    String createPayment(PaymentRequestDto paymentRequestDto);
    void deletePayment(PaymentDeleteRequestDto paymentDeleteRequestDto);
}
