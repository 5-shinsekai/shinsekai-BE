package com.example.shinsekai.purchase.dto.in;

import com.example.shinsekai.purchase.entity.PurchaseProductList;
import com.example.shinsekai.purchase.vo.in.PurchaseProductRequestVo;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class PurchaseProductListRequestDto {
    private Long productOptionId;
    private String purchaseCode;
    private String productCode;
    private String productName;
    private Integer productPrice;
    private Integer quantity;
    private String productImageUrl;
    private String productImageDescription;

    public PurchaseProductList toEntity(){
        return PurchaseProductList.builder()
                .productOptionId(productOptionId)
                .purchaseCode(purchaseCode)
                .productCode(productCode)
                .productName(productName)
                .productPrice(productPrice)
                .quantity(quantity)
                .productImageUrl(productImageUrl)
                .productImageDescription(productImageDescription)
                .build();
    }

    public static PurchaseProductListRequestDto from(PurchaseProductRequestVo purchaseProductRequestVo, String purchaseCode){
        return PurchaseProductListRequestDto.builder()
                .productOptionId(purchaseProductRequestVo.getProductOptionId())
                .purchaseCode(purchaseCode)
                .productCode(purchaseProductRequestVo.getProductCode())
                .productName(purchaseProductRequestVo.getProductName())
                .productPrice(purchaseProductRequestVo.getProductPrice())
                .quantity(purchaseProductRequestVo.getQuantity())
                .productImageUrl(purchaseProductRequestVo.getProductImageUrl())
                .productImageDescription(purchaseProductRequestVo.getProductImageDescription())
                .build();
    }
}
