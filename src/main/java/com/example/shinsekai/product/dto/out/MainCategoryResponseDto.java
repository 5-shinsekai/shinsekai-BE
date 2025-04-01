package com.example.shinsekai.product.dto.out;

import com.example.shinsekai.product.entity.category.MainCategory;
import com.example.shinsekai.product.vo.out.MainCategoryResponseVo;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MainCategoryResponseDto {
    private Long id;
    private String name;
    private String categoryImage;
    private String categoryImageAltText;

    @Builder
    public MainCategoryResponseDto(Long id, String name, String categoryImage, String categoryImageAltText) {
        this.id = id;
        this.name = name;
        this.categoryImage = categoryImage;
        this.categoryImageAltText = categoryImageAltText;
    }

    public static MainCategoryResponseDto from(MainCategory category) {
        return MainCategoryResponseDto.builder()
                .id(category.getId())
                .name(category.getName())
                .categoryImage(category.getCategoryImage())
                .categoryImageAltText(category.getCategoryImageAltText())
                .build();
    }

    public static MainCategoryResponseVo toVo(MainCategoryResponseDto dto){
        return MainCategoryResponseVo.builder()
                .code(dto.getId())
                .name(dto.getName())
                .image(dto.getCategoryImage())
                .imageAltText(dto.getCategoryImageAltText())
                .build();
    }
}
