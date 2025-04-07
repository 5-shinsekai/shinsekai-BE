package com.example.shinsekai.category.entity;

import com.example.shinsekai.common.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
public class ProductCategoryList extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
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
