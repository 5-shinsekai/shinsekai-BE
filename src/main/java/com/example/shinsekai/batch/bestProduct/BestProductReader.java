package com.example.shinsekai.batch.bestProduct;

import com.example.shinsekai.purchase.entity.PurchaseProductList;
import com.example.shinsekai.purchase.infrastructure.PurchaseProductListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.database.JpaPagingItemReader;

@RequiredArgsConstructor
public class BestProductReader extends JpaPagingItemReader<PurchaseProductList> {

    private final PurchaseProductListRepository purchaseProductListRepository;


}
