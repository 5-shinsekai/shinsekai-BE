package com.example.shinsekai.product.vo.out;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductOutlineResponseVo {

    private String productCode;
    private String productName;
    private double productPrice;
    private String thumbnailUrl;
    private int discountRate;

    @Builder
    public ProductOutlineResponseVo(
            String productCode,
            String productName,
            double productPrice,
            String thumbnailUrl,
            int discountRate) {
        this.productCode = productCode;
        this.productName = productName;
        this.productPrice = productPrice;
        this.thumbnailUrl = thumbnailUrl;
        this.discountRate = discountRate;
    }
}
