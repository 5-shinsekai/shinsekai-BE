package com.example.shinsekai.category.vo.in;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductCategoryListCreateRequestVo {
    private String productCode;
    private Long mainCategoryId;
    private Long subCategoryId;

    @Builder
    public ProductCategoryListCreateRequestVo(String productCode, Long mainCategoryId, Long subCategoryId) {
        this.productCode = productCode;
        this.mainCategoryId = mainCategoryId;
        this.subCategoryId = subCategoryId;
    }
}
