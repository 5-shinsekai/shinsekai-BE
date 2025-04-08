package com.example.shinsekai.category.vo.in;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SubCategoryCreateRequestVo {
    private String name;
    private Long mainCategoryId;

    @Builder
    public SubCategoryCreateRequestVo(String name, Long mainCategoryId) {
        this.name = name;
        this.mainCategoryId = mainCategoryId;
    }
}
