package com.example.shinsekai.category.application;

import com.example.shinsekai.category.dto.in.MainCategoryCreateRequestDto;
import com.example.shinsekai.category.dto.in.MainCategoryUpdateRequestDto;
import com.example.shinsekai.category.dto.out.CategoryFilterResponseDto;
import com.example.shinsekai.category.dto.out.MainCategoryResponseDto;

import java.util.List;

public interface MainCategoryService {
    // MainCategory
    List<MainCategoryResponseDto> getAllMainCategory(); // getALlCategory
    void createMainCategory(MainCategoryCreateRequestDto mainCategoryCreateRequestDto);
    void softDeleteMainCategory(Long categoryId);
    void updateMainCategory(MainCategoryUpdateRequestDto mainCategoryUpdateRequestDto);

    // CategoryFilter
    CategoryFilterResponseDto getCategoryFilter(Long mainCategoryId);
}
