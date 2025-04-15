package com.example.shinsekai.event.vo.out;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductEventListGetResponseVo {
    private Long id;
    private String productCode;

    @Builder
    public ProductEventListGetResponseVo(Long id, String productCode) {
        this.id = id;
        this.productCode = productCode;
    }
}