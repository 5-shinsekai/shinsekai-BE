package com.example.shinsekai.category.dto.in;

import com.example.shinsekai.category.entity.ProductCategoryList;
import com.example.shinsekai.category.vo.in.ProductCategoryListUpdateRequestVo;
import jakarta.validation.constraints.Null;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductCategoryListUpdateRequestDto {
    private Long id;
    private String productCode;
    private Long mainCategoryId;
    private Long subCategoryId;

    @Builder
    public ProductCategoryListUpdateRequestDto(Long id, String productCode, Long mainCategoryId, Long subCategoryId) {
        this.id = id;
        this.productCode = productCode;
        this.mainCategoryId = mainCategoryId;
        this.subCategoryId = subCategoryId;
    }

    public static ProductCategoryListUpdateRequestDto from(ProductCategoryListUpdateRequestVo vo){
        return ProductCategoryListUpdateRequestDto.builder()
                .id(vo.getId())
                .mainCategoryId(vo.getMainCategoryId())
                .subCategoryId(vo.getSubCategoryId())
                .productCode(vo.getProductCode())
                .build();
    }

    public ProductCategoryList toEntity(ProductCategoryList productCategoryList) {
        return ProductCategoryList.builder()
                .id(id)
                .productCode(productCode == null ? productCategoryList.getProductCode() : productCode)
                .mainCategoryId(mainCategoryId == null ? productCategoryList.getMainCategoryId() : mainCategoryId)
                .subCategoryId(subCategoryId == null ? productCategoryList.getSubCategoryId() : subCategoryId)
                .build();
    }
}
