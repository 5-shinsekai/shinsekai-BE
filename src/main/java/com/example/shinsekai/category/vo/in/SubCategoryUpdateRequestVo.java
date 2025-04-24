package com.example.shinsekai.category.vo.in;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SubCategoryUpdateRequestVo {
    private Long categoryId;
    private String name;
    private Long mainCategoryId;

    @Builder
    public SubCategoryUpdateRequestVo(Long categoryId, String name, Long mainCategoryId) {
        this.categoryId = categoryId;
        this.name = name;
        this.mainCategoryId = mainCategoryId;
    }
}
