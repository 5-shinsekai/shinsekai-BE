package com.example.shinsekai.product.entity;

import com.example.shinsekai.common.entity.BaseEntity;
import com.example.shinsekai.product.entity.category.ProductAndCategory;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "product")
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_PK")
    private Long id;

    @Column(name = "PRODUCT_CODE", nullable = false, length = 255, unique = true)
    private String productCode;

    @Column(name = "PRODUCT_PRICE")
    private int productPrice;

    @Column(name = "PRODUCT_NAME", nullable = false, length = 255)
    private String productName;

    @Enumerated(EnumType.STRING)
    @Column(name = "PRODUCT_STATUS", nullable = false)
    private ProductStatus productStatus; // SELLING, HIDDEN, DELETED

    @Column(name = "PRODUCT_SUMMARY", nullable = false, length = 255)
    private String productSummary; // 제품요약

    @Column(name = "PRODUCT_DESCRIPTION_PATH", nullable = false, length = 255)
    private String productDescriptionPath; // 제품설명 이미지 경로

    @Column(name = "VIEW_COUNT", nullable = false)
    private int viewCount;

    @Column(name = "PRODUCT_IMAGE_PATH", nullable = false, columnDefinition = "TEXT")
    private String productImagePath; // 썸네일 이미지 경로

    @Column(name = "LIMIT_COUNT")
    private int limitCount; // 회원당 구매 가능 제한개수

    @Column(name = "FREEZED_CHECKED", nullable = false)
    private Boolean freezedChecked; // 냉동보관여부

    @Column(name = "SYMBOL_CHECKED")
    private Boolean symbolChecked; // 각인기능여부

    @Column(name = "DISCOUNT_RATE", nullable = false)
    private int discountRate;

    @Column(name = "MIN_STOCK_COUNT", nullable = false)
    private int minStockCount; // 최소고개수

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductAndCategory> productAndCategories = new ArrayList<>();
}
