package com.example.shinsekai.option.dto.in;

import com.example.shinsekai.option.entity.Color;
import com.example.shinsekai.option.vo.in.ColorRequestVo;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ColorRequestDto {

    private String colorName;

    @Builder
    public ColorRequestDto(String colorName) {
        this.colorName = colorName;
    }

    public Color toEntity() {
        return Color.builder()
                .colorName(colorName).
                build();
    }

    public static ColorRequestDto from(ColorRequestVo colorRequestVo) {
        return ColorRequestDto.builder()
                .colorName(colorRequestVo.getColorName())
                .build();
    }
}
