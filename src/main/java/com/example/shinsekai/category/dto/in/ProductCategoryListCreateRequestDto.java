package com.example.shinsekai.category.dto.in;

import com.example.shinsekai.category.entity.ProductCategoryList;
import com.example.shinsekai.category.vo.in.ProductCategoryListCreateRequestVo;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductCategoryListCreateRequestDto {
    private String productCode;
    private Long mainCategoryId;
    private Long subCategoryId;

    @Builder
    public ProductCategoryListCreateRequestDto(String productCode, Long mainCategoryId, Long subCategoryId) {
        this.productCode = productCode;
        this.mainCategoryId = mainCategoryId;
        this.subCategoryId = subCategoryId;
    }

    public static ProductCategoryListCreateRequestDto from(ProductCategoryListCreateRequestVo vo){
        return ProductCategoryListCreateRequestDto.builder()
                .productCode(vo.getProductCode())
                .mainCategoryId(vo.getMainCategoryId())
                .subCategoryId(vo.getSubCategoryId())
                .build();
    }

    public ProductCategoryList toEntity(){
        return ProductCategoryList.builder()
                .productCode(productCode)
                .mainCategoryId(mainCategoryId)
                .subCategoryId(subCategoryId)
                .build();
    }
}
