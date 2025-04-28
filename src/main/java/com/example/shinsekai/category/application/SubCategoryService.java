package com.example.shinsekai.category.application;

import com.example.shinsekai.category.dto.in.SubCategoryCreateRequestDto;
import com.example.shinsekai.category.dto.in.SubCategoryUpdateRequestDto;
import com.example.shinsekai.category.dto.out.SubCategoryResponseDto;

import java.util.List;

public interface SubCategoryService {
    List<SubCategoryResponseDto> getAllSubCategory(Long categoryId);

    void createSubCategory(SubCategoryCreateRequestDto subCategoryCreateRequestDto);

    void deleteSubCategory(Long categoryId);

    void updateSubCategory(SubCategoryUpdateRequestDto subCategoryUpdateRequestDto);

}
