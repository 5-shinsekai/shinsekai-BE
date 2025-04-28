package com.example.shinsekai.purchase.application;

import com.example.shinsekai.purchase.dto.in.PurchaseDeleteRequestDto;
import com.example.shinsekai.purchase.dto.in.PurchaseProductListRequestDto;
import com.example.shinsekai.purchase.dto.in.PurchaseRequestDto;
import com.example.shinsekai.purchase.dto.out.PurchaseProductListResponseDto;
import com.example.shinsekai.purchase.dto.out.PurchaseResponseDto;
import com.example.shinsekai.purchase.dto.out.PurchaseStatusResponseDto;

import java.util.List;

public interface PurchaseService {
    void createPurchase(PurchaseRequestDto purchaseRequestDto, List<PurchaseProductListRequestDto> purchaseProductListRequestDtoList);

    void deletePurchase(PurchaseDeleteRequestDto purchaseDeleteRequestDto);

    List<PurchaseResponseDto> findMemberPurchaseList(String memberUuid);

    List<PurchaseProductListResponseDto> findPurchaseProductListByPurchaseCode(String purchaseCode);

    PurchaseStatusResponseDto findPurchaseStatusByMemberUuid(String memberUuid);
}