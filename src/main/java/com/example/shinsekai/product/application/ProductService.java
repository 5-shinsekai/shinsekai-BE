package com.example.shinsekai.product.application;

import com.example.shinsekai.product.dto.in.ProductRequestDto;
import com.example.shinsekai.product.dto.out.ProductResponseDto;

import java.util.List;

public interface ProductService {

    void createProduct(ProductRequestDto productRequestDto);

    ProductResponseDto getSellingProduct(String productCode);

    List<ProductResponseDto> getAllSellingProducts();

    void updateProduct(String productCode, ProductRequestDto productRequestDto);

    void hideProduct(String productCode);

    void showProduct(String productCode);

    void hardDeleteProduct(String productCode);

    void softDeleteProduct(String productCode);

    void restoreProduct(String productCode);

}
