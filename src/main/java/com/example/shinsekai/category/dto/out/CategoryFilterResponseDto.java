package com.example.shinsekai.category.dto.out;

import com.example.shinsekai.category.vo.out.CategoryFilterResponseVo;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class CategoryFilterResponseDto {
    private List<SubCategoryResponseDto> subCategories;
    private List<CommonFilterItemDto> seasons;
    private List<CommonFilterItemDto> sizes;
    private List<CommonFilterItemDto> colors;
    private List<CommonFilterItemDto> priceRanges;

    @Builder
    public CategoryFilterResponseDto(List<SubCategoryResponseDto> subCategories, List<CommonFilterItemDto> seasons,
                                     List<CommonFilterItemDto> sizes, List<CommonFilterItemDto> colors,
                                     List<CommonFilterItemDto> priceRanges) {
        this.subCategories = subCategories;
        this.seasons = seasons;
        this.sizes = sizes;
        this.colors = colors;
        this.priceRanges = priceRanges;
    }

    public CategoryFilterResponseVo toVo(){
        return CategoryFilterResponseVo.builder()
                .subCategories(subCategories.stream().map(SubCategoryResponseDto::toVo).toList())
                .seasons(seasons.stream().map(CommonFilterItemDto::toVo).toList())
                .sizes(sizes.stream().map(CommonFilterItemDto::toVo).toList())
                .colors(colors.stream().map(CommonFilterItemDto::toVo).toList())
                .priceRanges(priceRanges.stream().map(CommonFilterItemDto::toVo).toList())
                .build();
    }
}
