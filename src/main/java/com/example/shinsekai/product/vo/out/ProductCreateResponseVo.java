package com.example.shinsekai.product.vo.out;

import com.example.shinsekai.product.entity.ProductStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ProductCreateResponseVo {

    private String productCode;
    private String productName;
    private int productPrice;
    private ProductStatus productStatus;
    private String productSummary;
    private String contentImages;
    private String thumbnailUrl;
    private int userPurchaseLimit;
    private boolean isFrozen;
    private boolean isEngraving;
    private int discountRate;

    @Builder
    public ProductCreateResponseVo(String productCode,
                                   String productName,
                                   int productPrice,
                                   ProductStatus productStatus,
                                   String productSummary,
                                   String contentImages,
                                   String thumbnailUrl,
                                   int userPurchaseLimit,
                                   boolean isFrozen,
                                   boolean isEngraving,
                                   int discountRate) {
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
    }
}
