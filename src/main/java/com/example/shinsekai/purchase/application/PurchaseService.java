package com.example.shinsekai.purchase.application;

import com.example.shinsekai.purchase.dto.in.PurchaseProductListRequestDto;
import com.example.shinsekai.purchase.dto.in.PurchaseRequestDto;
import com.example.shinsekai.purchase.dto.in.PurchaseTemporaryRequestDto;

import java.util.List;

public interface PurchaseService {
    String createTemporaryPurchase(String token, PurchaseTemporaryRequestDto purchaseTemporaryRequestDto);
    void createPurchase(String Token, PurchaseRequestDto purchaseRequestDto, List<PurchaseProductListRequestDto> purchaseProductListRequestDtoList);
}
