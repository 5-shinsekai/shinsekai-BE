package com.example.shinsekai.category.vo.in;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SubCategoryUpdateRequestVo {
    private String name;
    private Long mainCategoryId;

    @Builder
    public SubCategoryUpdateRequestVo(String name, Long mainCategoryId) {
        this.name = name;
        this.mainCategoryId = mainCategoryId;
    }
}
