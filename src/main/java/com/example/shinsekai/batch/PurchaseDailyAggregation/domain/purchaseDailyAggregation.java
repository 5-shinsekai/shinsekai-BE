package com.example.shinsekai.batch.PurchaseDailyAggregation.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDate;

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
    @Setter
    private LocalDate aggregateAt;

    @Builder
    public purchaseDailyAggregation(String productCode, String productName, Long mainCategoryId, Long quantity) {
        this.productCode = productCode;
        this.productName = productName;
        this.mainCategoryId = mainCategoryId;
        this.quantity = quantity;
    }

}
