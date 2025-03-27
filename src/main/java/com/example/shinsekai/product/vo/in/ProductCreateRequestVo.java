package com.example.shinsekai.product.vo.in;

import com.example.shinsekai.product.entity.ProductStatus;
import lombok.Getter;

@Getter
public class ProductCreateRequestVo {

    private String productCode;
    private String productName;
    private double productPrice;
    private ProductStatus productStatus;
    private String productSummary;
    private String contentImages;
    private String thumbnailUrl;
    private int userPurchaseLimit;
    private boolean frozen;
    private boolean engraving;
    private int discountRate;
}
