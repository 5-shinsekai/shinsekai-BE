package com.example.shinsekai.product.dto.out;

import com.example.shinsekai.option.entity.ProductOptionList;
import com.example.shinsekai.product.entity.Product;
import com.example.shinsekai.product.entity.ProductStatus;
import com.example.shinsekai.product.vo.out.ProductResponseVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
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
    private boolean isNew;
    private boolean isBest;


    @Builder
    public ProductResponseDto(String productCode,
                              String productName,
                              double productPrice,
                              ProductStatus productStatus,
                              String productSummary,
                              String contentImages,
                              String thumbnailUrl,
                              int userPurchaseLimit,
                              boolean isFrozen,
                              boolean isEngraving,
                              int discountRate,
                              List<Long> productOptionIds,
                              boolean isNew,
                              boolean isBest) {
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
        this.productOptionIds = productOptionIds;
        this.isNew = isNew;
        this.isBest = isBest;
    }


    public static ProductResponseDto from(Product product, List<ProductOptionList> productOptionLists, boolean isBest) {
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
                .isNew(product.isNewProduct())
                .isBest(isBest)
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
                .productOptionIds(productOptionIds)
                .isNew(isNew)
                .isBest(isBest)
                .build();
    }
}

