package com.example.shinsekai.product.application;

import com.example.shinsekai.product.dto.in.MainCategoryCreateRequestDto;
import com.example.shinsekai.product.dto.in.MainCategoryUpdateRequestDto;
import com.example.shinsekai.product.dto.out.MainCategoryResponseDto;

import java.util.List;

public interface CategoryService {
    // MainCategory
    List<MainCategoryResponseDto> getAllMainCategory(); // getALlCategory
    void createMainCategory(MainCategoryCreateRequestDto mainCategoryCreateRequestDto);
    void softDeleteMainCategory(Long categoryId);
    void updateMainCategory(MainCategoryUpdateRequestDto mainCategoryUpdateRequestDto);
//
//    //SubCategory
//    List<CategoryResponseDto> getAllSubCategory();
//    void createSubCategory();
//    void softDeleteSubCategory();
//    void updateSubCategory();
//
//    // ProductCategoryList
//    void createProductCategoryList();
//    void softDeleteProductCategoryList();
//    void updateProductCategoryList();
}
