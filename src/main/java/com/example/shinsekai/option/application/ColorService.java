package com.example.shinsekai.option.application;


import com.example.shinsekai.option.dto.in.ColorRequestDto;
import com.example.shinsekai.option.dto.out.ColorResponseDto;

import java.util.List;

public interface ColorService {
    void createColor(ColorRequestDto dto);

    void updateColor(Long id, ColorRequestDto dto);

    void deleteColor(Long id);

    List<ColorResponseDto> getAllColors();

    ColorResponseDto getColor(Long id);
}
