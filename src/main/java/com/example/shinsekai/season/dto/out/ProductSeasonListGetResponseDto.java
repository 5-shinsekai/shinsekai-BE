package com.example.shinsekai.season.dto.out;

import com.example.shinsekai.season.entity.ProductSeasonList;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductSeasonListGetResponseDto {
    private String productCode;

    @Builder
    public ProductSeasonListGetResponseDto(String productCode) {
        this.productCode = productCode;
    }

    public static ProductSeasonListGetResponseDto from(ProductSeasonList productSeasonList) {
        return ProductSeasonListGetResponseDto.builder()
                .productCode(productSeasonList.getProductCode())
                .build();
    }
}
