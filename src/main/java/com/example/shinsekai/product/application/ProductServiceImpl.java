package com.example.shinsekai.product.application;

import com.example.shinsekai.common.entity.BaseResponseStatus;
import com.example.shinsekai.common.exception.BaseException;
import com.example.shinsekai.product.dto.in.ProductCreateRequestDto;
import com.example.shinsekai.product.dto.out.ProductCreateResponseDto;
import com.example.shinsekai.product.entity.Product;
import com.example.shinsekai.product.infrastructure.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    @Transactional
    public ProductCreateResponseDto createProduct(ProductCreateRequestDto productCreateRequestDto) {
        return ProductCreateResponseDto.from(productRepository.save(productCreateRequestDto.toEntity()));
    }

    @Override
    @Transactional
    public ProductCreateResponseDto updateProduct(String productCode, ProductCreateRequestDto productCreateRequestDto) {
        Product product = productRepository.findByProductCode(productCode)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_PRODUCT));

        product.updateFromDto(productCreateRequestDto);
        return ProductCreateResponseDto.from(productRepository.save(product));
    }
}
