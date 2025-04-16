package com.example.shinsekai.like.vo.out;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductLikeResponseVo {

    private Long productLikeId;
    private String productCode;

    @Builder
    public ProductLikeResponseVo(Long productLikeId,
                                 String productCode) {
        this.productLikeId = productLikeId;
        this.productCode = productCode;
    }
}
