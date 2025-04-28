package com.example.shinsekai.category.application;

import com.example.shinsekai.category.dto.in.MainCategoryCreateRequestDto;
import com.example.shinsekai.category.dto.in.MainCategoryUpdateRequestDto;
import com.example.shinsekai.category.dto.out.MainCategoryResponseDto;

import java.util.List;

public interface MainCategoryService {
    List<MainCategoryResponseDto> getAllMainCategory();

    void createMainCategory(MainCategoryCreateRequestDto mainCategoryCreateRequestDto);

    void deleteMainCategory(Long categoryId);

    void updateMainCategory(MainCategoryUpdateRequestDto mainCategoryUpdateRequestDto);
}
