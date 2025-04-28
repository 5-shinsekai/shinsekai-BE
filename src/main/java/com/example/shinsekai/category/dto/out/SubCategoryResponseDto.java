package com.example.shinsekai.category.dto.out;

import com.example.shinsekai.category.entity.SubCategory;
import com.example.shinsekai.category.vo.out.SubCategoryResponseVo;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SubCategoryResponseDto {
    private Long id;
    private String name;

    @Builder
    public SubCategoryResponseDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static SubCategoryResponseDto from(SubCategory subCategory) {
        return SubCategoryResponseDto.builder()
                .id(subCategory.getId())
                .name(subCategory.getName())
                .build();
    }

    public SubCategoryResponseVo toVo() {
        return SubCategoryResponseVo.builder()
                .code(id)
                .name(name)
                .build();
    }
}
