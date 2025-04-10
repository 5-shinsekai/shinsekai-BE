package com.example.shinsekai.category.vo.out;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductCategoryListGetResponseVo {
    private Long id;
    private String productCode;
    private Long mainCategoryId;
    private Long subCateogryId;

    @Builder
    public ProductCategoryListGetResponseVo(Long id, String productCode, Long mainCategoryId, Long subCateogryId) {
        this.id = id;
        this.productCode = productCode;
        this.mainCategoryId = mainCategoryId;
        this.subCateogryId = subCateogryId;
    }
}
