package com.example.shinsekai.purchase.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PurchaseStatus {
    DONE,
    CANCEL;
}
