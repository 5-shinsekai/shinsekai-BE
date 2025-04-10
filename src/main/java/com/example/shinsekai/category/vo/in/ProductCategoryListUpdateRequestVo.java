package com.example.shinsekai.category.vo.in;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductCategoryListUpdateRequestVo {
    private Long id;
    private String productCode;
    private Long mainCategoryId;
    private Long subCategoryId;

    @Builder
    public ProductCategoryListUpdateRequestVo(Long id, String productCode, Long mainCategoryId, Long subCategoryId) {
        this.id = id;
        this.productCode = productCode;
        this.mainCategoryId = mainCategoryId;
        this.subCategoryId = subCategoryId;
    }
}
