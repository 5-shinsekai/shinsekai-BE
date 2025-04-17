package com.example.shinsekai.batch.bestProduct.domain;

import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseProductCategory {
    private String productCode;
    private String productName;
    private Long mainCategoryId;
    private Integer quantity;

}
