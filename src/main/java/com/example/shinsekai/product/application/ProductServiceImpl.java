package com.example.shinsekai.product.application;

import com.example.shinsekai.batch.bestProduct.infrastructure.BestProductRepository;
import com.example.shinsekai.common.entity.BaseResponseStatus;
import com.example.shinsekai.common.exception.BaseException;
import com.example.shinsekai.option.entity.ProductOptionList;
import com.example.shinsekai.option.infrastructure.ProductOptionListRepository;
import com.example.shinsekai.product.dto.in.ProductRequestDto;
import com.example.shinsekai.product.dto.out.ProductOutlineResponseDto;
import com.example.shinsekai.product.dto.out.ProductResponseDto;
import com.example.shinsekai.product.entity.Product;
import com.example.shinsekai.product.entity.ProductStatus;
import com.example.shinsekai.product.infrastructure.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductOptionListRepository productOptionListRepository;
    private final BestProductRepository bestProductRepository;

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

        List<ProductOptionList> optionLists = productOptionListRepository.findAllByProductCode(productCode);
        boolean isBest = bestProductRepository.existsByProductCode(productCode);
        return ProductResponseDto.from(product, optionLists, isBest);
    }

    //  ProductCode 페이징 조회
    @Override
    public Page<String> getAllSellingProductCodes(Pageable pageable) {
        return productRepository.findProductCodesByIsDeletedFalseAndProductStatusSelling(pageable);
    }

    //  개요 조회
    @Override
    public ProductOutlineResponseDto getSellingProductOutline(String productCode) {
        Product product = productRepository.findByProductCodeAndIsDeletedFalse(productCode)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_PRODUCT));

        boolean isBest = bestProductRepository.existsByProductCode(productCode);
        return ProductOutlineResponseDto.from(product, isBest);
    }

    //  상품 수정
    @Override
    @Transactional
    public void updateProduct(ProductRequestDto productRequestDto) {
        Product product = productRepository.findByProductCode(productRequestDto.getProductCode())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_PRODUCT));

        product.update(productRequestDto);
    }

    //  상품 상태 변경 (SELLING ↔ HIDDEN)
    @Override
    @Transactional
    public void toggleProductStatus(String productCode) {
        Product product = productRepository.findByProductCodeAndIsDeletedFalse(productCode)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_PRODUCT));

        if (product.getProductStatus() == ProductStatus.SELLING) {
            product.changeStatus(ProductStatus.HIDDEN);
        } else if (product.getProductStatus() == ProductStatus.HIDDEN) {
            product.changeStatus(ProductStatus.SELLING);
        } else {
            throw new BaseException(BaseResponseStatus.INVALID_INPUT);
        }
    }

    //  하드 딜리트
    @Override
    @Transactional
    public void hardDeleteProduct(String productCode) {
        Product product = productRepository.findByProductCode(productCode)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_PRODUCT));
        productRepository.delete(product);
    }

    //  소프트 딜리트
    @Override
    @Transactional
    public void softDeleteProduct(String productCode) {
        Product product = productRepository.findByProductCode(productCode)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_PRODUCT));

        product.setDeleted();
    }


}
