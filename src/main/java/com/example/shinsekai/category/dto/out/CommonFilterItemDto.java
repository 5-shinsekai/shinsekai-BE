package com.example.shinsekai.category.dto.out;

import com.example.shinsekai.category.vo.out.CommonFilterItemVo;
import com.example.shinsekai.common.enums.ColorType;
import com.example.shinsekai.common.enums.PriceRangeType;
import com.example.shinsekai.common.enums.SizeType;
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

    public static CommonFilterItemDto from(SizeType sizeType) {
        return CommonFilterItemDto.builder()
                .id(sizeType.getId())
                .name(sizeType.getName())
                .build();
    }

    public static CommonFilterItemDto from(ColorType colorType) {
        return CommonFilterItemDto.builder()
                .id(colorType.getId())
                .name(colorType.getName())
                .build();
    }

    public static CommonFilterItemDto from(PriceRangeType priceRangeType) {
        return CommonFilterItemDto.builder()
                .id(priceRangeType.getId())
                .name(priceRangeType.getName())
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
