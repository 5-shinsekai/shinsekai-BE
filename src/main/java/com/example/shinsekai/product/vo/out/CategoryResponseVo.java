package com.example.shinsekai.product.vo.out;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CategoryResponseVo {
    private Long code;
    private String name;

    @Builder
    public CategoryResponseVo(Long code, String name) {
        this.code = code;
        this.name = name;
    }
}
