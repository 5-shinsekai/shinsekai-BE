package com.example.shinsekai.product.dto.in;

import com.example.shinsekai.product.entity.Product;
import com.example.shinsekai.product.entity.ProductStatus;
import com.example.shinsekai.product.vo.in.ProductCreateRequestVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@NoArgsConstructor
@Getter
public class ProductCreateRequestDto {

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

    @Builder
    public ProductCreateRequestDto(
                                   String productName,
                                   double productPrice,
                                   ProductStatus productStatus,
                                   String productSummary,
                                   String contentImages,
                                   String thumbnailUrl,
                                   int userPurchaseLimit,
                                   boolean isFrozen,
                                   boolean isEngraving,
                                   int discountRate) {
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
    }

    public Product toEntity() {
        return Product.builder()
                .productCode(generateProductCode())
                .productName(this.productName)
                .productPrice(this.productPrice)
                .productStatus(this.productStatus)
                .productSummary(this.productSummary)
                .contentImages(this.contentImages)
                .thumbnailUrl(this.thumbnailUrl)
                .userPurchaseLimit(this.userPurchaseLimit)
                .isFrozen(this.isFrozen)
                .isEngraving(this.isEngraving)
                .discountRate(this.discountRate)
                .build();
    }

    public static ProductCreateRequestDto from(ProductCreateRequestVo productCreateRequestVo) {
        return ProductCreateRequestDto.builder()
                .productName(productCreateRequestVo.getProductName())
                .productPrice(productCreateRequestVo.getProductPrice())
                .productStatus(productCreateRequestVo.getProductStatus())
                .productSummary(productCreateRequestVo.getProductSummary())
                .contentImages(productCreateRequestVo.getContentImages())
                .thumbnailUrl(productCreateRequestVo.getThumbnailUrl())
                .userPurchaseLimit(productCreateRequestVo.getUserPurchaseLimit())
                .isFrozen(productCreateRequestVo.isFrozen())
                .isEngraving(productCreateRequestVo.isEngraving())
                .discountRate(productCreateRequestVo.getDiscountRate())
                .build();
    }

    public static String generateProductCode() {
        String prefix = "P";
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String uuidPart = UUID.randomUUID().toString().substring(0, 8); // or nanoId()

        return prefix + date + "-" + uuidPart;
    }

}
