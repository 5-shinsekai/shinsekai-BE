package com.example.shinsekai.product.vo.out;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MainCategorysGetResponseVo {
    private Long categoryId;
    private String categoryName;
    private String categoryImage;

    @Builder
    public MainCategorysGetResponseVo(Long categoryId, String categoryName, String categoryImage) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryImage = categoryImage;
    }
}
