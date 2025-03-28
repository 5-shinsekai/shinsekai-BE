package com.example.shinsekai.product.entity;

import com.example.shinsekai.common.entity.BaseEntity;
import com.example.shinsekai.product.dto.in.ProductRequestDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@Table(name = "product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "PRODUCT_CODE", nullable = false, length = 255, unique = true, updatable = false)
    private String productCode;

    @Column(name = "PRODUCT_NAME", nullable = false, length = 100)
    private String productName;

    @Column(name = "PRODUCT_PRICE", nullable = false, length = 100)
    private double productPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "PRODUCT_STATUS", nullable = false)
    private ProductStatus productStatus; // SELLING, HIDDEN, DELETED

    @Column(name = "PRODUCT_SUMMARY", nullable = false, length = 255)
    private String productSummary; // 제품요약

    @Lob
    @Column(name = "content_images", nullable = false)
    private String contentImages; // 제품설명 이미지

    /*    @Column(name = "VIEW_COUNT", nullable = false)
        private int viewCount;*/ // 집계 테이블에 넣어서 관리
    @Lob
    @Column(name = "thumbnail_url", nullable = false)
    private String thumbnailUrl;

    @Column(name = "user_purchase_limit")
    private int userPurchaseLimit;

    @Column(name = "is_frozen", nullable = false)
    private boolean isFrozen;

    @Column(name = "is_engraving")
    private boolean isEngraving;

    @Column(name = "discount_Rate", nullable = false)
    private int discountRate;

    /*@Column(name = "MIN_STOCK_COUNT", nullable = false)
    private int minStockCount; // 최소 재고개수 // 옵션에 ㄱㄱ*/

    @Builder
    public Product(Long id,
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

        this.id = id;
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

    public void updateFromDto(ProductRequestDto dto) {
        this.productName = dto.getProductName();
        this.productPrice = dto.getProductPrice();
        this.productStatus = dto.getProductStatus();
        this.productSummary = dto.getProductSummary();
        this.contentImages = dto.getContentImages();
        this.thumbnailUrl = dto.getThumbnailUrl();
        this.userPurchaseLimit = dto.getUserPurchaseLimit();
        this.isFrozen = dto.isFrozen();
        this.isEngraving = dto.isEngraving();
        this.discountRate = dto.getDiscountRate();
    }
}
