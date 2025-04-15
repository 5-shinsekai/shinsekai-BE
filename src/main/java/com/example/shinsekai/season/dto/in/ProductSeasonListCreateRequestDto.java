package com.example.shinsekai.season.dto.in;

import com.example.shinsekai.season.entity.ProductSeasonList;
import com.example.shinsekai.season.vo.in.ProductSeasonListCreateRequestVo;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductSeasonListCreateRequestDto {
    private String productCode;
    private Integer seasonId;

    @Builder
    public ProductSeasonListCreateRequestDto(String productCode, Integer seasonId) {
        this.productCode = productCode;
        this.seasonId = seasonId;
    }

    public static ProductSeasonListCreateRequestDto from(ProductSeasonListCreateRequestVo vo) {
        return ProductSeasonListCreateRequestDto.builder()
                .productCode(vo.getProductCode())
                .seasonId(vo.getSeasonId())
                .build();
    }

    public ProductSeasonList toEntity() {
        return ProductSeasonList.builder()
                .productCode(productCode)
                .seasonId(seasonId)
                .build();
    }
}
