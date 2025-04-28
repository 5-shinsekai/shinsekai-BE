package com.example.shinsekai.category.dto.in;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductCategoryListDeleteRequestDto {
    private Long id;

    @Builder
    public ProductCategoryListDeleteRequestDto(Long id) {
        this.id = id;
    }

    public static ProductCategoryListDeleteRequestDto from(Long id) {
        return ProductCategoryListDeleteRequestDto.builder()
                .id(id)
                .build();
    }
}
