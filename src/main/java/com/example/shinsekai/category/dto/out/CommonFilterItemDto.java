package com.example.shinsekai.category.dto.out;

import com.example.shinsekai.category.entity.PriceRange;
import com.example.shinsekai.category.vo.out.CommonFilterItemVo;
import com.example.shinsekai.option.entity.Color;
import com.example.shinsekai.option.entity.Size;
import com.example.shinsekai.season.entity.Season;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CommonFilterItemDto {
    private int id;
    private String name;

    @Builder
    public CommonFilterItemDto(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static CommonFilterItemDto from(Size size) {
        return CommonFilterItemDto.builder()
                .id(size.getId().intValue())
                .name(size.getSizeName())
                .build();
    }

    public static CommonFilterItemDto from(Color color) {
        return CommonFilterItemDto.builder()
                .id(color.getId().intValue())
                .name(color.getColorName())
                .build();
    }

    public static CommonFilterItemDto from(PriceRange priceRange) {
        return CommonFilterItemDto.builder()
                .id(priceRange.getId())
                .name(priceRange.getName())
                .build();
    }

    public static CommonFilterItemDto from(Season season) {
        return CommonFilterItemDto.builder()
                .id(season.getId())
                .name(season.getSeasonName())
                .build();
    }

    public CommonFilterItemVo toVo(){
        return CommonFilterItemVo.builder()
                .code(id)
                .name(name)
                .build();
    }
}
