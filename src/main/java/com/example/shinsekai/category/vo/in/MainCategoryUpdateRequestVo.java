package com.example.shinsekai.category.vo.in;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MainCategoryUpdateRequestVo {
    private String name;
    private String categoryImage;
    private String categoryImageAltText;

    @Builder
    public MainCategoryUpdateRequestVo(String name, String categoryImage, String categoryImageAltText) {
        this.name = name;
        this.categoryImage = categoryImage;
        this.categoryImageAltText = categoryImageAltText;
    }
}
