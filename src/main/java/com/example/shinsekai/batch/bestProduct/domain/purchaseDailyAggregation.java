package com.example.shinsekai.batch.bestProduct.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@Entity
public class purchaseDailyAggregation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String productCode;
    private String productName;
    private Long mainCategoryId;
    private Long quantity;

    @Builder
    public purchaseDailyAggregation(String productCode, String productName, Long mainCategoryId, Long quantity) {
        this.productCode = productCode;
        this.productName = productName;
        this.mainCategoryId = mainCategoryId;
        this.quantity = quantity;
    }

    public void sumQuantity(Long quantity) {
        this.quantity += quantity;
    }
}
