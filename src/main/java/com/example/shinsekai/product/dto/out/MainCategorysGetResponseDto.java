package com.example.shinsekai.product.dto.out;

import com.example.shinsekai.product.entity.category.MainCategory;
import com.example.shinsekai.product.vo.out.MainCategorysGetResponseVo;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class MainCategorysGetResponseDto {
    private Long id;
    private String name;
    private String categoryImage;

    @Builder
    public MainCategorysGetResponseDto(Long id, String name, String categoryImage) {
        this.id = id;
        this.name = name;
        this.categoryImage = categoryImage;
    }

    public static MainCategorysGetResponseDto from(MainCategory category) {
        return MainCategorysGetResponseDto.builder()
                .id(category.getId())
                .name(category.getName())
                .categoryImage(category.getCategoryImage())
                .build();
    }

    public static MainCategorysGetResponseVo toVo(MainCategorysGetResponseDto dto){
        return MainCategorysGetResponseVo.builder()
                .categoryId(dto.getId())
                .categoryName(dto.getName())
                .categoryImage(dto.getCategoryImage())
                .build();
    }
}
