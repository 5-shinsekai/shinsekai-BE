package com.example.shinsekai.product.dto.in;

import com.example.shinsekai.product.entity.category.SubCategory;
import com.example.shinsekai.product.vo.out.CategoryResponseVo;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CategoryResponseDto {
    private Long id;
    private String name;

    @Builder
    public CategoryResponseDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static CategoryResponseDto from(SubCategory subCategory){
        return CategoryResponseDto.builder()
                .id(subCategory.getId())
                .name(subCategory.getName())
                .build();
    }

    public CategoryResponseVo toVo(){
        return CategoryResponseVo.builder()
                .code(id)
                .name(name)
                .build();
    }
}
