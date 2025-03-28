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

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    @Transactional
    public void createProduct(ProductRequestDto productRequestDto) {
        ProductResponseDto.from(productRepository.save(productRequestDto.toEntity()));
    }

    @Override
    public ProductResponseDto getProduct(String productCode) {
        Product product = productRepository.findByProductCode(productCode)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_PRODUCT));
        return ProductResponseDto.from(product);
    }

    @Override
    public List<ProductResponseDto> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(ProductResponseDto::from)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void updateProduct(String productCode, ProductRequestDto productRequestDto) {
        Product product = productRepository.findByProductCode(productCode)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_PRODUCT));

        product.updateFromDto(productRequestDto);
        ProductResponseDto.from(productRepository.save(product));
    }

    @Override
    @Transactional
    public void deleteProduct(String productCode) {
        Product product = productRepository.findByProductCode(productCode)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_PRODUCT));
        productRepository.delete(product);
    }

}
