package com.example.shinsekai.product.vo.in;

import com.example.shinsekai.product.entity.ProductStatus;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class ProductRequestVo {

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

    public boolean getIsFrozen() {
        return isFrozen;
    }

    public boolean getIsEngraving() {
        return isEngraving;
    }
}
