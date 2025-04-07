package com.example.shinsekai.option.application;

import com.example.shinsekai.option.dto.in.SizeRequestDto;
import com.example.shinsekai.option.dto.out.SizeResponseDto;

import java.util.List;

public interface SizeService {

    void createSize(SizeRequestDto dto);

    void updateSize(SizeRequestDto dto);

    void deleteSize(Long id);

    List<SizeResponseDto> getAllSizes();

    SizeResponseDto getSize(Long id);
}
