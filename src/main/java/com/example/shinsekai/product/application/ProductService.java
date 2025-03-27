package com.example.shinsekai.product.application;

import com.example.shinsekai.product.dto.in.ProductCreateRequestDto;
import com.example.shinsekai.product.dto.out.ProductCreateResponseDto;

public interface ProductService {

    ProductCreateResponseDto createProduct(ProductCreateRequestDto productCreateRequestDto);
}
