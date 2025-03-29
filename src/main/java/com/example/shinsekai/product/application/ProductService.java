package com.example.shinsekai.product.application;

import com.example.shinsekai.product.dto.in.ProductRequestDto;
import com.example.shinsekai.product.dto.out.ProductResponseDto;
import com.example.shinsekai.product.entity.Product;

public interface ProductService {

    ProductResponseDto createProduct(ProductRequestDto productRequestDto);

    ProductResponseDto getProduct(String productCode);

    ProductResponseDto updateProduct(String productCode, ProductRequestDto productRequestDto);

    void deleteProduct(String productCode);

}
