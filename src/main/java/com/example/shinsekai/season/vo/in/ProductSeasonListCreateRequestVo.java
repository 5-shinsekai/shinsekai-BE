package com.example.shinsekai.season.vo.in;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductSeasonListCreateRequestVo {
    @NotNull
    private String productCode;
    @NotNull
    private Integer seasonId;

    @Builder
    public ProductSeasonListCreateRequestVo(String productCode, Integer seasonId) {
        this.productCode = productCode;
        this.seasonId = seasonId;
    }
}
