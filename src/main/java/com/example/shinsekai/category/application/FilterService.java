package com.example.shinsekai.category.application;

import com.example.shinsekai.category.dto.out.CategoryFilterResponseDto;

public interface FilterService {
    CategoryFilterResponseDto getCategoryFilter(Long mainCategoryId);
}
