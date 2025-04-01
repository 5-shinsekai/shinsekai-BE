package com.example.shinsekai.product.vo.out;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MainCategoryResponseVo {
    private Long code;
    private String name;
    private String image;
    private String imageAltText;

    @Builder
    public MainCategoryResponseVo(Long code, String name, String image, String imageAltText) {
        this.code = code;
        this.name = name;
        this.image = image;
        this.imageAltText = imageAltText;
    }
}
