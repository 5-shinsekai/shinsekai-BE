package com.example.shinsekai.category.application;

import com.example.shinsekai.category.dto.in.*;
import com.example.shinsekai.category.dto.out.ProductCategoryListGetResponseDto;

import java.util.List;

public interface ProductCategoryListService {
    void createProductCategoryList(ProductCategoryListCreateRequestDto productCategoryListCreateRequestDto);

    ProductCategoryListGetResponseDto getProductCategoryList(
            ProductCategoryListGetRequestDto productCategoryListGetRequestDto);

    ProductCategoryListGetResponseDto getProductCategoryListByProductCode(
            ProductCategoryListGetByProductCodeRequestDto dto);

    void updateProductCategoryList(ProductCategoryListUpdateRequestDto productCategoryListUpdateRequestDto);

    void deleteProductCategoryList(ProductCategoryListDeleteRequestDto productCategoryListDeleteRequestDto);

    void deleteAllProductCategoryList(
            List<ProductCategoryListDeleteRequestDto> productCategoryListDeleteRequestDtoLsit);
}
