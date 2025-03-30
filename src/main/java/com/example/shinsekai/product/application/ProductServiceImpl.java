package com.example.shinsekai.product.application;

import com.example.shinsekai.common.entity.BaseResponseStatus;
import com.example.shinsekai.common.exception.BaseException;
import com.example.shinsekai.product.dto.in.ProductRequestDto;
import com.example.shinsekai.product.dto.out.ProductResponseDto;
import com.example.shinsekai.product.entity.Product;
import com.example.shinsekai.product.entity.ProductStatus;
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

    //  상품 생성
    @Override
    @Transactional
    public void createProduct(ProductRequestDto productRequestDto) {
        ProductResponseDto.from(productRepository.save(productRequestDto.toEntity()));
    }

    //  판매 중인 상품 조회
    @Override
    public ProductResponseDto getSellingProduct(String productCode) {
        Product product = productRepository.findByProductCodeAndIsDeletedFalse(productCode)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_PRODUCT));

        if (product.getProductStatus() == ProductStatus.HIDDEN) {
            throw new BaseException(BaseResponseStatus.NO_ACCESS_AUTHORITY);
        }

        return ProductResponseDto.from(product);
    }

    //  판매 중인 상품 전체 조회
    @Override
    public List<ProductResponseDto> getAllSellingProducts() {
        return productRepository.findAllByIsDeletedFalseAndProductStatus(ProductStatus.SELLING)
                .stream()
                .map(ProductResponseDto::from)
                .collect(Collectors.toList());
    }

    //  상품 수정
    @Override
    @Transactional
    public void updateProduct(String productCode, ProductRequestDto productRequestDto) {
        Product product = productRepository.findByProductCode(productCode)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_PRODUCT));

        product.updateFromDto(productRequestDto);
        ProductResponseDto.from(productRepository.save(product));
    }

    //  상품 상태 HIDDEN 으로 변경
    @Override
    @Transactional
    public void hideProduct(String productCode) {
        Product product = productRepository.findByProductCodeAndIsDeletedFalse(productCode)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_PRODUCT));

        product.changeStatus(ProductStatus.HIDDEN);
    }

    //  상품 상태 SELLING 으로 변경
    @Override
    @Transactional
    public void showProduct(String productCode) {
        Product product = productRepository.findByProductCodeAndIsDeletedFalse(productCode)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_PRODUCT));

        product.changeStatus(ProductStatus.SELLING);
    }

    //  하드 딜리트
    @Override
    @Transactional
    public void hardDeleteProduct(String productCode) {
        Product product = productRepository.findByProductCode(productCode)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_PRODUCT));
        productRepository.delete(product);
    }

    @Override
    @Transactional
    public void softDeleteProduct(String productCode) {
        Product product = productRepository.findByProductCode(productCode)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_PRODUCT));

        product.setDeleted();
    }

    // 상품 복구
    @Override
    @Transactional
    public void restoreProduct(String productCode) {
        Product product = productRepository.findByProductCode(productCode)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_PRODUCT));

        if (!product.isDeleted()) {
            throw new BaseException(BaseResponseStatus.INVALID_INPUT);
        }

        product.restore();
    }

}
