package com.example.shinsekai.option.application;


import com.example.shinsekai.option.dto.in.ColorRequestDto;

public interface ColorService {
    void createColor(ColorRequestDto dto);

    void updateColor(Long id,ColorRequestDto dto);

    void deleteColor(Long id);
}
