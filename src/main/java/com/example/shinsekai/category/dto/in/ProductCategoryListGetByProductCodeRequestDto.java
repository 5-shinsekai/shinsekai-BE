package com.example.shinsekai.category.dto.in;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductCategoryListGetByProductCodeRequestDto {
    private String productCode;

    @Builder
    public ProductCategoryListGetByProductCodeRequestDto(String productCode) {
        this.productCode = productCode;
    }

    public static ProductCategoryListGetByProductCodeRequestDto from(String productCode){
        return ProductCategoryListGetByProductCodeRequestDto.builder()
                .productCode(productCode)
                .build();
    }
}
