package com.example.shinsekai.product.vo.out;

import com.example.shinsekai.product.entity.ProductStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class ProductResponseVo {

    private String productCode;
    private String productName;
    private double productPrice;
    private ProductStatus productStatus;
    private String productSummary;
    private String contentImages;
    private String thumbnailUrl;
    private int userPurchaseLimit;
    private boolean isFrozen;
    private boolean isEngraving;
    private int discountRate;
    private List<Long> productOptionIds;
    private boolean isNew;
    private boolean isBest;

    @Builder
    public ProductResponseVo(
            String productCode,
            String productName,
            double productPrice,
            ProductStatus productStatus,
            String productSummary,
            String contentImages,
            String thumbnailUrl,
            int userPurchaseLimit,
            boolean isFrozen,
            boolean isEngraving,
            int discountRate,
            List<Long> productOptionIds,
            boolean isNew,
            boolean isBest) {
        this.productCode = productCode;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productStatus = productStatus;
        this.productSummary = productSummary;
        this.contentImages = contentImages;
        this.thumbnailUrl = thumbnailUrl;
        this.userPurchaseLimit = userPurchaseLimit;
        this.isFrozen = isFrozen;
        this.isEngraving = isEngraving;
        this.discountRate = discountRate;
        this.productOptionIds = productOptionIds;
        this.isNew = isNew;
        this.isBest = isBest;
    }
}
