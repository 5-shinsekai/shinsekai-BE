package com.example.shinsekai.event.dto.out;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductEventListGetResponseDto {
    private Long id;
    private String productCode;

    @Builder
    public ProductEventListGetResponseDto(Long id, String productCode) {
        this.id = id;
        this.productCode = productCode;
    }
}
