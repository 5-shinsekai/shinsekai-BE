package com.example.shinsekai.product.dto.in;

import com.example.shinsekai.product.entity.Product;
import com.example.shinsekai.product.entity.ProductStatus;
import com.example.shinsekai.product.vo.in.ProductRequestVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@NoArgsConstructor
@Getter
@ToString
@Builder(toBuilder = true)
public class ProductRequestDto {

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

    @Builder
    public ProductRequestDto(
            String productCode,
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
    }

    public static ProductRequestDto from(ProductRequestVo productRequestVo) {
        return ProductRequestDto.builder()
                .productCode(productRequestVo.getProductCode())
                .productName(productRequestVo.getProductName())
                .productPrice(productRequestVo.getProductPrice())
                .productStatus(productRequestVo.getProductStatus())
                .productSummary(productRequestVo.getProductSummary())
                .contentImages(productRequestVo.getContentImages())
                .thumbnailUrl(productRequestVo.getThumbnailUrl())
                .userPurchaseLimit(productRequestVo.getUserPurchaseLimit())
                .isFrozen(productRequestVo.getIsFrozen())
                .isEngraving(productRequestVo.getIsEngraving())
                .discountRate(productRequestVo.getDiscountRate())
                .build();
    }

    public static String generateProductCode() {
        String prefix = "P";
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String uuidPart = UUID.randomUUID().toString().substring(0, 8);

        return prefix + date + "-" + uuidPart;
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

}
