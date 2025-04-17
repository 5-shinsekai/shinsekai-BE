package com.example.shinsekai.batch.bestProduct.application;

import com.example.shinsekai.batch.bestProduct.domain.BestProduct;
import com.example.shinsekai.batch.bestProduct.domain.ProductRank;
import com.example.shinsekai.batch.bestProduct.domain.PurchaseProductCategory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import java.time.LocalDate;
import java.util.*;

/*
* Todo : 일단 순위를 매기는 요소를 구매 수량으로만 고정. 차후 찜하기 등 추가 할 수도...
* */
@Slf4j
public class BestProductProcessor implements ItemProcessor<PurchaseProductCategory, BestProduct> {

    private final Map<String, Long> productCategoryMap = new HashMap<>();
    private final Map<String, Integer> productCountMap = new HashMap<>();

    @Override
    public BestProduct process(PurchaseProductCategory list) throws Exception {

        log.info("----------------list : {}", list);

        productCategoryMap.putIfAbsent(list.getProductCode(), list.getMainCategoryId());
        productCountMap.merge(list.getProductCode(), list.getQuantity(), Integer::sum);


        return null;
    }

    /**
     * 프로세서가 끝난 후 집계 데이터를 BestProduct 리스트로 반환
     */
    public List<BestProduct> getAggregatedResults() {
        // 1. 카테고리별로 상품들 묶기
        Map<Long, List<ProductRank>> categoryMap = new HashMap<>();

        // 상품 수량과 카테고리 정보를 바탕으로 카테고리별로 정리
        for (Map.Entry<String, Integer> entry : productCountMap.entrySet()) {
            String productCode = entry.getKey();
            int totalQuantity = entry.getValue();
            Long categoryId = productCategoryMap.get(productCode);

            categoryMap
                    .computeIfAbsent(categoryId, id -> new ArrayList<>())
                    .add(new ProductRank(productCode, totalQuantity));
        }
        System.out.println("~~~~~~~~categoryMap~~~~~~~~~~"+categoryMap.toString());
        // 2. 각 카테고리 내에서 수량 기준으로 정렬 + BestProduct로 변환
        List<BestProduct> bestProducts = new ArrayList<>();
        // 카테고리별로 순위 매기기
        for (Map.Entry<Long, List<ProductRank>> categoryEntry : categoryMap.entrySet()) {
            Long categoryId = categoryEntry.getKey();
            List<ProductRank> sortedRanks = categoryEntry.getValue().stream()
                    .sorted(Comparator.comparingInt(ProductRank::getTotalQuantity).reversed()) // 수량 기준 내림차순 정렬
                    .toList();

            // 순위 매기기
            int rank = 1;
            for (ProductRank productRank : sortedRanks) {
                BestProduct bestProduct = new BestProduct(
                        null, // ID는 자동 생성되므로 null
                        productRank.getProductCode(),
                        categoryId,
                        productRank.getTotalQuantity(),
                        rank++, // 순위는 내림차순으로 정렬된 순서대로 매겨짐
                        LocalDate.now() // orderDate는 배치 작업에서 자동으로 설정
                );
                bestProducts.add(bestProduct);
            }
        }
        log.info("Processed BestProduct: {}", bestProducts);
        return bestProducts;
    }

}