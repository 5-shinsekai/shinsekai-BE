package com.example.shinsekai.product.application;

import com.example.shinsekai.common.entity.BaseResponseStatus;
import com.example.shinsekai.common.exception.BaseException;
import com.example.shinsekai.product.dto.in.ProductRequestDto;
import com.example.shinsekai.product.dto.out.ProductResponseDto;
import com.example.shinsekai.product.entity.Product;
import com.example.shinsekai.product.infrastructure.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    @Transactional
    public ProductResponseDto createProduct(ProductRequestDto productRequestDto) {
        return ProductResponseDto.from(productRepository.save(productRequestDto.toEntity()));
    }

    @Override
    public ProductResponseDto getProduct(String productCode) {
        Product product = productRepository.findByProductCode(productCode)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_PRODUCT));
        return ProductResponseDto.from(product);
    }

    @Override
    @Transactional
    public ProductResponseDto updateProduct(String productCode, ProductRequestDto productRequestDto) {
        Product product = productRepository.findByProductCode(productCode)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_PRODUCT));

        product.updateFromDto(productRequestDto);
        return ProductResponseDto.from(productRepository.save(product));
    }

    @Override
    @Transactional
    public void deleteProduct(String productCode) {
        Product product = productRepository.findByProductCode(productCode)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_PRODUCT));
        productRepository.delete(product);
    }

}
