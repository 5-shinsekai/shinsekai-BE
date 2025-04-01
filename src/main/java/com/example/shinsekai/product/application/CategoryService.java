package com.example.shinsekai.product.application;

import com.example.shinsekai.product.dto.in.*;
import com.example.shinsekai.product.dto.out.MainCategoryResponseDto;

import java.util.List;

public interface CategoryService {
    // MainCategory
    List<MainCategoryResponseDto> getAllMainCategory(); // getALlCategory
    void createMainCategory(MainCategoryCreateRequestDto mainCategoryCreateRequestDto);
    void softDeleteMainCategory(Long categoryId);
    void updateMainCategory(MainCategoryUpdateRequestDto mainCategoryUpdateRequestDto);

    //SubCategory
    List<CategoryResponseDto> getAllSubCategory(Long categoryId);
    void createSubCategory(SubCategoryCreateRequestDto subCategoryCreateRequestDto);
    void softDeleteSubCategory(Long categoryId);
    void updateSubCategory(SubCategoryUpdateRequestDto subCategoryUpdateRequestDto);

//    // ProductCategoryList
//    void createProductCategoryList();
//    void softDeleteProductCategoryList();
//    void updateProductCategoryList();
}
