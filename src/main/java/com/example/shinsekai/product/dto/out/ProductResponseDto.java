package com.example.shinsekai.product.dto.out;

import com.example.shinsekai.option.entity.ProductOptionList;
import com.example.shinsekai.product.entity.Product;
import com.example.shinsekai.product.entity.ProductStatus;
import com.example.shinsekai.product.vo.out.ProductResponseVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDto {

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


    public static ProductResponseDto from(Product product, List<ProductOptionList> productOptionLists) {
        List<Long> productOptionIds = productOptionLists.stream()
                .map(ProductOptionList::getId)
                .toList();

        return ProductResponseDto.builder()
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
                .productOptionIds(productOptionIds)
                .build();
    }

    public static ProductResponseDto from(Product product) {
        return ProductResponseDto.builder()
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

    public ProductResponseVo toVo() {
        return ProductResponseVo.builder()
                .productCode(productCode)
                .build();
    }
}

