package com.example.shinsekai.purchase.vo.out;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class PurchaseProductResponseVo {
    private Long productOptionId;
    private String productCode;
    private String productName;
    private Double productPrice;
    private Integer quantity;
    private String productImageUrl;
    private String productImageDescription;
}
