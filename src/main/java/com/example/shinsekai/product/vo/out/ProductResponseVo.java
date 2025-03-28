package com.example.shinsekai.product.vo.out;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ProductResponseVo {

    private String productCode;

    @Builder
    public ProductResponseVo(String productCode) {
        this.productCode = productCode;
    }
}
