package com.example.shinsekai.category.dto.in;

import com.example.shinsekai.category.entity.MainCategory;
import com.example.shinsekai.category.vo.in.MainCategoryCreateRequestVo;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MainCategoryCreateRequestDto {
    private String name;
    private String categoryImage;
    private String categoryImageAltText;

    @Builder
    public MainCategoryCreateRequestDto(String name, String categoryImage, String categoryImageAltText) {
        this.name = name;
        this.categoryImage = categoryImage;
        this.categoryImageAltText = categoryImageAltText;
    }

    public static MainCategoryCreateRequestDto from(MainCategoryCreateRequestVo mainCategoryCreateRequestVo) {
        return MainCategoryCreateRequestDto.builder()
                .name(mainCategoryCreateRequestVo.getName())
                .categoryImage(mainCategoryCreateRequestVo.getCategoryImage())
                .categoryImageAltText(mainCategoryCreateRequestVo.getCategoryImageAltText())
                .build();
    }

    public MainCategory toEntity() {
        return MainCategory.builder()
                .name(name)
                .categoryImage(categoryImage)
                .categoryImageAltText(categoryImageAltText)
                .build();
    }
}
