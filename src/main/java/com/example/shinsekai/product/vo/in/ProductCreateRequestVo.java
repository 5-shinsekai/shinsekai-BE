package com.example.shinsekai.product.vo.in;

import com.example.shinsekai.product.entity.ProductStatus;
import lombok.Getter;

@Getter
public class ProductCreateRequestVo {

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
}
