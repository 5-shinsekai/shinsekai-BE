package com.example.shinsekai.purchase.application;

import com.example.shinsekai.purchase.dto.in.CancelOrderRequestDto;
import com.example.shinsekai.purchase.dto.in.OrderRequestDto;
import com.example.shinsekai.purchase.dto.in.PurchaseDeleteRequestDto;
import com.example.shinsekai.purchase.dto.in.PurchaseProductListRequestDto;

import java.util.List;

public interface OrderService {
    public void createOrder(OrderRequestDto orderRequestDto, List<PurchaseProductListRequestDto> purchaseProductList);
    public void deleteOrder(CancelOrderRequestDto cancelOrderRequestDto);
}
