package com.example.shinsekai.product.dto.out;

import com.example.shinsekai.product.entity.Product;
import com.example.shinsekai.product.entity.ProductStatus;
import com.example.shinsekai.product.vo.out.ProductCreateResponseVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductCreateResponseDto {

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

    public static ProductCreateResponseDto from(Product product) {
        return ProductCreateResponseDto.builder()
                .productCode(product.getProductCode())
                .productName(product.getProductName())
                .productPrice(product.getProductPrice())
                .productStatus(product.getProductStatus())
                .productSummary(product.getProductSummary())
                .contentImages(product.getContentImages())
                .thumbnailUrl(product.getThumbnailUrl())
                .userPurchaseLimit(product.getUserPurchaseLimit())
                .isFrozen(product.isFrozen())
                .isEngraving(product.isEngraving())
                .discountRate(product.getDiscountRate())
                .build();
    }

    public ProductCreateResponseVo toVo() {
        return ProductCreateResponseVo.builder()
                .productCode(productCode)
                .productName(productName)
                .productPrice(productPrice)
                .productStatus(productStatus)
                .productSummary(productSummary)
                .contentImages(contentImages)
                .thumbnailUrl(thumbnailUrl)
                .userPurchaseLimit(userPurchaseLimit)
                .isFrozen(isFrozen)
                .isEngraving(isEngraving)
                .discountRate(discountRate)
                .build();
    }
}

