package com.example.shinsekai.season.vo.in;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductSeasonListUpdateRequestVo {
    @NotNull
    private Long id;
    private String productCode;
    private Integer seasonId;

    @Builder
    public ProductSeasonListUpdateRequestVo(Long id, String productCode, Integer seasonId) {
        this.id = id;
        this.productCode = productCode;
        this.seasonId = seasonId;
    }
}
