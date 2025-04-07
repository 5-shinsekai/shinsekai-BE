package com.example.shinsekai.category.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
public class ProductCategoryList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productCode;

    private Long mainCategoryId;

    private Long subCategoryId;

//    @ManyToOne(fetch = FetchType.LAZY)
//    private MainCategory mainCategory;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    private SubCategory subCategory;

    @Builder
    public ProductCategoryList(Long id, String productCode, Long mainCategoryId, Long subCategoryId) {
        this.id = id;
        this.productCode = productCode;
        this.mainCategoryId = mainCategoryId;
        this.subCategoryId = subCategoryId;
    }
}
