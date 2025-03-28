package com.example.shinsekai.product.application;

import com.example.shinsekai.product.dto.in.ProductRequestDto;
import com.example.shinsekai.product.dto.out.ProductResponseDto;

import java.util.List;

public interface ProductService {

    void createProduct(ProductRequestDto productRequestDto);

    ProductResponseDto getProduct(String productCode);

    void updateProduct(String productCode, ProductRequestDto productRequestDto);

    void deleteProduct(String productCode);

    List<ProductResponseDto> getAllProducts();

}
