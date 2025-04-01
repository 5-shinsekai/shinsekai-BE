package com.example.shinsekai.product.vo.in;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SubCategoryUpdateRequestVo {
    private Long id;
    private String name;
    private Long mainCategoryId;

    @Builder
    public SubCategoryUpdateRequestVo(Long id, String name, Long mainCategoryId) {
        this.id = id;
        this.name = name;
        this.mainCategoryId = mainCategoryId;
    }
}
