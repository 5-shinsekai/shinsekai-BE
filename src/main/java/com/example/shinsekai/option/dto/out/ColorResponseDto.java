package com.example.shinsekai.option.dto.out;

import com.example.shinsekai.option.entity.Color;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ColorResponseDto {
    private Long id;
    private String colorName;

    public static ColorResponseDto from(Color color) {
        return new ColorResponseDto(color.getId(), color.getColorName());
    }
}
