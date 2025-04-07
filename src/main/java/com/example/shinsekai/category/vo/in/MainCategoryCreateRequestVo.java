package com.example.shinsekai.category.vo.in;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MainCategoryCreateRequestVo {
    private String name;
    private String categoryImage;
    private String categoryImageAltText;

    @Builder
    public MainCategoryCreateRequestVo(String name, String categoryImage, String categoryImageAltText) {
        this.name = name;
        this.categoryImage = categoryImage;
        this.categoryImageAltText = categoryImageAltText;
    }
}
