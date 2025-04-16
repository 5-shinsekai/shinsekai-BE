package com.example.shinsekai.purchase.dto.out;

import com.example.shinsekai.purchase.entity.PurchaseProductList;
import com.example.shinsekai.purchase.vo.out.PurchaseProductResponseVo;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PurchaseProductListResponseDto {
    private String purchaseCode;
    private String productCode;
    private Long productOptionId;
    private String productName;
    private Double productPrice;
    private Integer quantity;
    private String productImageUrl;
    private String productImageDescription;

    public PurchaseProductResponseVo toVo(){
        return PurchaseProductResponseVo.builder()
                .productOptionId(productOptionId)
                .productCode(productCode)
                .productName(productName)
                .productPrice(productPrice)
                .quantity(quantity)
                .productImageUrl(productImageUrl)
                .productImageDescription(productImageDescription)
                .build();
    }

    public static PurchaseProductListResponseDto from(PurchaseProductList purchaseProductList){
        return PurchaseProductListResponseDto.builder()
                .productCode(purchaseProductList.getProductCode())
                .productOptionId(purchaseProductList.getProductOptionId())
                .productName(purchaseProductList.getProductName())
                .productPrice(purchaseProductList.getProductPrice())
                .quantity(purchaseProductList.getQuantity())
                .productImageUrl(purchaseProductList.getProductImageUrl())
                .productImageDescription(purchaseProductList.getProductImageDescription())
                .build();
    }
}
