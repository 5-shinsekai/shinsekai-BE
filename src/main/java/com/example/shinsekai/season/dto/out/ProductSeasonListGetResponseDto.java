package com.example.shinsekai.season.dto.out;

import com.example.shinsekai.season.entity.ProductSeasonList;
import com.example.shinsekai.season.vo.out.ProductSeasonListGetResponseVo;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductSeasonListGetResponseDto {
    private Long id;
    private String productCode;

    @Builder
    public ProductSeasonListGetResponseDto(Long id, String productCode) {
        this.id = id;
        this.productCode = productCode;
    }

    public static ProductSeasonListGetResponseDto from(ProductSeasonList productSeasonList) {
        return ProductSeasonListGetResponseDto.builder()
                .id(productSeasonList.getId())
                .productCode(productSeasonList.getProductCode())
                .build();
    }

    public ProductSeasonListGetResponseVo toVo() {
        return ProductSeasonListGetResponseVo.builder()
                .id(id)
                .productCode(productCode)
                .build();
    }
}
