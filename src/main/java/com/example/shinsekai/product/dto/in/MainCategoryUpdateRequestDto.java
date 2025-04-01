package com.example.shinsekai.product.dto.in;

import com.example.shinsekai.product.entity.category.MainCategory;
import com.example.shinsekai.product.vo.in.MainCategoryUpdateRequestVo;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MainCategoryUpdateRequestDto {
    private Long id;
    private String name;
    private String categoryImage;
    private String categoryImageAltText;

    @Builder
    public MainCategoryUpdateRequestDto(Long id, String name, String categoryImage, String categoryImageAltText) {
        this.id = id;
        this.name = name;
        this.categoryImage = categoryImage;
        this.categoryImageAltText = categoryImageAltText;
    }

    public static MainCategoryUpdateRequestDto from(MainCategoryUpdateRequestVo mainCategoryUpdateRequestVo){
        return MainCategoryUpdateRequestDto.builder()
                .id(mainCategoryUpdateRequestVo.getId())
                .name(mainCategoryUpdateRequestVo.getName())
                .categoryImage(mainCategoryUpdateRequestVo.getCategoryImage())
                .categoryImageAltText(mainCategoryUpdateRequestVo.getCategoryImageAltText())
                .build();
    }

    public MainCategory toEntity(MainCategory mainCategory){
        return MainCategory.builder()
                .id(id)
                .name(name == null ? mainCategory.getName() : name)
                .categoryImage(categoryImage == null ? mainCategory.getCategoryImage() : categoryImage)
                .categoryImageAltText(categoryImageAltText == null
                        ? mainCategory.getCategoryImage() : categoryImageAltText)
                .build();
    }
}
