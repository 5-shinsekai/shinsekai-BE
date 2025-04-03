package com.example.shinsekai.purchase.application;

import com.example.shinsekai.purchase.dto.in.PurchaseRequestDto;
import com.example.shinsekai.purchase.dto.in.PurchaseTemporaryRequestDto;

public interface PurchaseService {
    String createTemporaryPurchase(String memberUuid, PurchaseTemporaryRequestDto purchaseTemporaryRequestDto);
    boolean validatePurchase(String purchaseUuid, double amount);
    void createPurchase(PurchaseRequestDto purchaseRequestDto);
}
