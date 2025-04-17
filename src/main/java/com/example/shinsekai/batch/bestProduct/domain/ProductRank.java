package com.example.shinsekai.batch.bestProduct.domain;

import lombok.*;

@Getter
@NoArgsConstructor
@ToString
public class ProductRank {
    private String productCode;
    private int totalQuantity;
    @Setter
    private int productRank;

    public ProductRank(String productCode, int totalQuantity) {
        this.productCode = productCode;
        this.totalQuantity = totalQuantity;
    }
}
