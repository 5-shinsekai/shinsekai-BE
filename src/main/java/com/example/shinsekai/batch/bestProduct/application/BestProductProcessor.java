package com.example.shinsekai.batch.bestProduct.application;

import com.example.shinsekai.batch.bestProduct.domain.BestProduct;
import com.example.shinsekai.batch.bestProduct.domain.purchaseDailyAggregation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

import java.util.*;

/*
* Todo : 일단 순위를 매기는 요소를 구매 수량으로만 고정. 차후 찜하기 등 추가 할 수도...
* */
@Slf4j
public class BestProductProcessor implements ItemProcessor<purchaseDailyAggregation, BestProduct> {

    private final Map<String, purchaseDailyAggregation> aggregatedMap = new HashMap<>();

    @Override
    public BestProduct process(purchaseDailyAggregation item) throws Exception {

        if(aggregatedMap.containsKey(item.getProductCode())) {
            item.sumQuantity(aggregatedMap.get(item.getProductCode()).getQuantity());

        }else{
            aggregatedMap.put(item.getProductCode(), item);
        }


        return null;// 뭘 줘야하지
    }

}