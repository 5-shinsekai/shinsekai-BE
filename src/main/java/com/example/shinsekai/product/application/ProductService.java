package com.example.shinsekai.product.application;

import com.example.shinsekai.product.dto.in.ProductRequestDto;
import com.example.shinsekai.product.dto.out.ProductOutlineResponseDto;
import com.example.shinsekai.product.dto.out.ProductResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    void createProduct(ProductRequestDto productRequestDto);

    Page<String> getAllSellingProductCodes(Pageable pageable);

    ProductOutlineResponseDto getSellingProductOutline(String productCode);

    ProductResponseDto getSellingProduct(String productCode);

    void updateProduct(ProductRequestDto productRequestDto);

    void toggleProductStatus(String productCode);

    void hardDeleteProduct(String productCode);

    void softDeleteProduct(String productCode);


}
