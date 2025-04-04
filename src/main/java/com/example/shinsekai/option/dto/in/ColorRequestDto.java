package com.example.shinsekai.option.dto.in;

import com.example.shinsekai.option.entity.Color;
import com.example.shinsekai.option.vo.in.ColorRequestVo;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ColorRequestDto {

    private Long id;
    private String colorName;

    @Builder
    public ColorRequestDto(Long id, String colorName) {
        this.id = id;
        this.colorName = colorName;
    }

    public Color toEntity() {
        return Color.builder()
                .id(id)
                .colorName(colorName).
                build();
    }

    public static ColorRequestDto from(ColorRequestVo colorRequestVo) {
        return ColorRequestDto.builder()
                .colorName(colorRequestVo.getColorName().toUpperCase())
                .build();
    }

    public static ColorRequestDto from(Long id, ColorRequestVo colorRequestVo) {
        return ColorRequestDto.builder()
                .id(id)
                .colorName(colorRequestVo.getColorName().toUpperCase())
                .build();
    }
}
