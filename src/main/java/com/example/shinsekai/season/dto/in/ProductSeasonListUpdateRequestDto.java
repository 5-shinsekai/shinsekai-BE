package com.example.shinsekai.season.dto.in;

import com.example.shinsekai.season.entity.ProductSeasonList;
import com.example.shinsekai.season.vo.in.ProductSeasonListUpdateRequestVo;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductSeasonListUpdateRequestDto {
    private Long id;
    private String productCode;
    private Integer seasonId;

    @Builder
    public ProductSeasonListUpdateRequestDto(Long id, String productCode, Integer seasonId) {
        this.id = id;
        this.productCode = productCode;
        this.seasonId = seasonId;
    }

    public static ProductSeasonListUpdateRequestDto from(ProductSeasonListUpdateRequestVo vo) {
        return ProductSeasonListUpdateRequestDto.builder()
                .id(vo.getId())
                .productCode(vo.getProductCode())
                .seasonId(vo.getSeasonId())
                .build();
    }

    public ProductSeasonList toEntity(ProductSeasonList productSeasonList) {
        return ProductSeasonList.builder()
                .id(id)
                .productCode(productCode == null ? productSeasonList.getProductCode() : productCode)
                .seasonId(seasonId == null ? productSeasonList.getSeasonId() : seasonId)
                .build();

    }
}
