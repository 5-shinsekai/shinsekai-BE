package com.example.shinsekai.category.entity;

import com.example.shinsekai.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ProductCategoryList extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50, unique = true)
    private String productCode;

    @Column(nullable = false)
    private Long mainCategoryId;

    private Long subCategoryId;

    @Builder
    public ProductCategoryList(Long id, String productCode, Long mainCategoryId, Long subCategoryId) {
        this.id = id;
        this.productCode = productCode;
        this.mainCategoryId = mainCategoryId;
        this.subCategoryId = subCategoryId;
    }
}
