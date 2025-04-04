package com.example.shinsekai.product.application;

import com.example.shinsekai.product.dto.in.ProductRequestDto;
import com.example.shinsekai.product.dto.out.ProductResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {

    void createProduct(ProductRequestDto productRequestDto);
//    void createProduct(ProductRequestDto productRequestDto, MultipartFile thumbnailFile, MultipartFile contentImageFile);

    ProductResponseDto getSellingProduct(String productCode);

    List<ProductResponseDto> getAllSellingProducts();

    void updateProduct(String productCode, ProductRequestDto productRequestDto);

    void toggleProductStatus(String productCode);

    void hardDeleteProduct(String productCode);

    void softDeleteProduct(String productCode);


}
