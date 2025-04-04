package com.example.shinsekai.category.vo.in;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MainCategoryCreateRequestVo {
    private String name;
    private String categoryImage;
    private String categoryImageAltText;
    private boolean isDeleted;

    @Builder
    public MainCategoryCreateRequestVo(String name, String categoryImage, String categoryImageAltText, boolean isDeleted) {
        this.name = name;
        this.categoryImage = categoryImage;
        this.categoryImageAltText = categoryImageAltText;
        this.isDeleted = isDeleted;
    }

    public boolean getIsDeleted(){
        return isDeleted;
    }
}
