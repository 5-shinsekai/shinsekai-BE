package com.example.shinsekai.option.application;

import com.example.shinsekai.option.dto.in.ProductOptionRequestDto;
import com.example.shinsekai.option.dto.out.ProductOptionResponseDto;

import java.util.List;

public interface ProductOptionService {
    void createOption(String productCode, ProductOptionRequestDto dto);

    List<ProductOptionResponseDto> getOptionsByProductCode(String productCode);
}
