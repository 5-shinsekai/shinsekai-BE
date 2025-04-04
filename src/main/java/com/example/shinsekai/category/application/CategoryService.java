package com.example.shinsekai.category.application;

import com.example.shinsekai.category.dto.in.MainCategoryCreateRequestDto;
import com.example.shinsekai.category.dto.in.MainCategoryUpdateRequestDto;
import com.example.shinsekai.category.dto.in.SubCategoryCreateRequestDto;
import com.example.shinsekai.category.dto.in.SubCategoryUpdateRequestDto;
import com.example.shinsekai.category.dto.out.CategoryFilterResponseDto;
import com.example.shinsekai.category.dto.out.SubCategoryResponseDto;
import com.example.shinsekai.category.dto.out.MainCategoryResponseDto;

import java.util.List;

public interface CategoryService {
    // MainCategory
    List<MainCategoryResponseDto> getAllMainCategory(); // getALlCategory
    void createMainCategory(MainCategoryCreateRequestDto mainCategoryCreateRequestDto);
    void softDeleteMainCategory(Long categoryId);
    void updateMainCategory(MainCategoryUpdateRequestDto mainCategoryUpdateRequestDto);

    //SubCategory
    List<SubCategoryResponseDto> getAllSubCategory(Long categoryId);
    void createSubCategory(SubCategoryCreateRequestDto subCategoryCreateRequestDto);
    void softDeleteSubCategory(Long categoryId);
    void updateSubCategory(SubCategoryUpdateRequestDto subCategoryUpdateRequestDto);

//    // ProductCategoryList
//    void createProductCategoryList();
//    void softDeleteProductCategoryList();
//    void updateProductCategoryList();

    // CategoryFilter
    CategoryFilterResponseDto getCategoryFilter(Long mainCategoryId);
}
