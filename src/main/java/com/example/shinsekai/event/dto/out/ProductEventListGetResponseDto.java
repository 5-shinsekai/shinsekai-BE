package com.example.shinsekai.event.dto.out;

import com.example.shinsekai.event.entity.ProductEventList;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductEventListGetResponseDto {
    private String productCode;

    @Builder
    public ProductEventListGetResponseDto(String productCode) {
        this.productCode = productCode;
    }

    public static ProductEventListGetResponseDto from(ProductEventList productEventList) {
        return ProductEventListGetResponseDto.builder()
                .productCode(productEventList.getProductCode())
                .build();
    }

}
