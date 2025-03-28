package com.example.shinsekai.product.application;

import com.example.shinsekai.product.dto.out.MainCategorysGetResponseDto;

import java.util.List;

public interface CategoryService {
    List<MainCategorysGetResponseDto> getMainCategorysName();
}
