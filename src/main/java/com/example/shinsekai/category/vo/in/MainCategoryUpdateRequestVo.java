package com.example.shinsekai.category.vo.in;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MainCategoryUpdateRequestVo {
    private Long categoryId;
    private String name;
    private String categoryImage;
    private String categoryImageAltText;

    @Builder
    public MainCategoryUpdateRequestVo(Long categoryId, String name, String categoryImage, String categoryImageAltText) {
        this.categoryId = categoryId;
        this.name = name;
        this.categoryImage = categoryImage;
        this.categoryImageAltText = categoryImageAltText;
    }
}
