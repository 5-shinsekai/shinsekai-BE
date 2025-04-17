package com.example.shinsekai.category.vo.out;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class CategoryFilterResponseVo {
    private List<CommonFilterItemVo> seasons;
    private List<CommonFilterItemVo> sizes;
    private List<CommonFilterItemVo> colors;
    private List<CommonFilterItemVo> priceRanges;

    @Builder

    public CategoryFilterResponseVo(List<CommonFilterItemVo> seasons, List<CommonFilterItemVo> sizes,
                                    List<CommonFilterItemVo> colors, List<CommonFilterItemVo> priceRanges) {
        this.seasons = seasons;
        this.sizes = sizes;
        this.colors = colors;
        this.priceRanges = priceRanges;
    }
}
