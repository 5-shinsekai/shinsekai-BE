package com.example.shinsekai.product.vo.in;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MainCategoryUpdateRequestVo {
    private Long id;
    private String name;
    private String categoryImage;
    private String categoryImageAltText;

    @Builder
    public MainCategoryUpdateRequestVo(Long id, String name, String categoryImage, String categoryImageAltText) {
        this.id = id;
        this.name = name;
        this.categoryImage = categoryImage;
        this.categoryImageAltText = categoryImageAltText;
    }
}
