package com.example.shinsekai.category.vo.in;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SubCategoryCreateRequestVo {
    private String name;
    private Long mainCategoryId;
    private boolean isDeleted;

    @Builder
    public SubCategoryCreateRequestVo(String name, Long mainCategoryId, boolean isDeleted) {
        this.name = name;
        this.mainCategoryId = mainCategoryId;
        this.isDeleted = isDeleted;
    }

}
