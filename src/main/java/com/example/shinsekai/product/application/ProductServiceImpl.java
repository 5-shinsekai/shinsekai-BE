package com.example.shinsekai.product.application;

import com.example.shinsekai.common.entity.BaseResponseStatus;
import com.example.shinsekai.common.exception.BaseException;
import com.example.shinsekai.product.dto.in.ProductCreateRequestDto;
import com.example.shinsekai.product.dto.out.ProductCreateResponseDto;
import com.example.shinsekai.product.entity.Product;
import com.example.shinsekai.product.infrastructure.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    @Transactional
    public ProductCreateResponseDto createProduct(ProductCreateRequestDto productCreateRequestDto) {

        boolean exists = productRepository.findByProductCode(productCreateRequestDto.getProductCode()).isPresent();

        if (exists) {
            throw new BaseException(BaseResponseStatus.DUPLICATED_PRODUCT);
        }

        return ProductCreateResponseDto.from(productRepository.save(productCreateRequestDto.toEntity()));
    }

}
