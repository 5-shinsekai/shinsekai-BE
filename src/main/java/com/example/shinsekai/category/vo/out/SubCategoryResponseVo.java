package com.example.shinsekai.category.vo.out;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SubCategoryResponseVo {
    private Long code;
    private String name;

    @Builder
    public SubCategoryResponseVo(Long code, String name) {
        this.code = code;
        this.name = name;
    }
}
