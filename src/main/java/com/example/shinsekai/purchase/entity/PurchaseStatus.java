package com.example.shinsekai.purchase.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PurchaseStatus {
    PAYMENT_COMPLETED,
    PREPARING, //주문완료
    SHIPPING,
    DELIVERED,
    CANCELLED;
}
