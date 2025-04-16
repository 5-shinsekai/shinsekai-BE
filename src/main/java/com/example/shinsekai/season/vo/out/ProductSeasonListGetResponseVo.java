package com.example.shinsekai.season.vo.out;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductSeasonListGetResponseVo {
    private Long id;
    private String productCode;

    @Builder
    public ProductSeasonListGetResponseVo(Long id, String productCode) {
        this.id = id;
        this.productCode = productCode;
    }
}
