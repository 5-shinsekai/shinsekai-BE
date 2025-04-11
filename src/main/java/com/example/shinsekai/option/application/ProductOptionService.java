package com.example.shinsekai.option.application;

import com.example.shinsekai.option.dto.in.ProductOptionRequestDto;
import com.example.shinsekai.option.dto.out.ProductOptionResponseDto;

import java.util.List;

public interface ProductOptionService {
    void createOption(ProductOptionRequestDto dto);

    ProductOptionResponseDto getProductOption(Long productOptionId);

    void deleteOption(Long productOptionId);
}
