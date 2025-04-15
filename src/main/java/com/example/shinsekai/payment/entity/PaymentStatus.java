package com.example.shinsekai.payment.entity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentStatus {
    DONE,
    CANCEL;
}
