package com.example.shinsekai.category.dto.in;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductCategoryListGetRequestDto {
    private Long id;

    @Builder
    public ProductCategoryListGetRequestDto(Long id) {
        this.id = id;
    }

    public static ProductCategoryListGetRequestDto from(Long id){
        return ProductCategoryListGetRequestDto.builder()
                .id(id)
                .build();
    }
}
