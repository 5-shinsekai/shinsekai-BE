package com.example.shinsekai.product.vo.out;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonProperty("isNew")
    private boolean isNew;

    @JsonProperty("isBest")
    private boolean isBest;

    @Builder
    public ProductOutlineResponseVo(
            String productCode,
            String productName,
            double productPrice,
            String thumbnailUrl,
            int discountRate,
            boolean isNew,
            boolean isBest) {
        this.productCode = productCode;
        this.productName = productName;
        this.productPrice = productPrice;
        this.thumbnailUrl = thumbnailUrl;
        this.discountRate = discountRate;
        this.isNew = isNew;
        this.isBest = isBest;
    }
}
