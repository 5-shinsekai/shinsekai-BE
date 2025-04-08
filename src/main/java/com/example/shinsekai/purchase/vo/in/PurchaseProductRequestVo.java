package com.example.shinsekai.purchase.vo.in;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PurchaseProductRequestVo {
    private Long productOptionId;
    private String purchaseCode;
    private String productCode;
    private String productName;
    private Integer productPrice;
    private Integer quantity;
    private String productImageUrl;
    private String productImageDescription;
}
