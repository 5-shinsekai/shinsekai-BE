package com.example.shinsekai.product.application;

import com.example.shinsekai.common.entity.BaseResponseStatus;
import com.example.shinsekai.common.exception.BaseException;
import com.example.shinsekai.common.service.S3Service;
import com.example.shinsekai.product.dto.in.ProductRequestDto;
import com.example.shinsekai.product.dto.out.ProductResponseDto;
import com.example.shinsekai.product.entity.Product;
import com.example.shinsekai.product.entity.ProductStatus;
import com.example.shinsekai.product.infrastructure.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final S3Service s3Service;

    //  상품 생성
    @Override
    @Transactional
    public void createProduct(ProductRequestDto productRequestDto) {
        ProductResponseDto.from(productRepository.save(productRequestDto.toEntity()));
    }

    /*@Override
    @Transactional
    public void createProduct(ProductRequestDto productRequestDto, MultipartFile thumbnailFile, MultipartFile contentImageFile) {
        try {
            String thumbnailUrl = s3Service.upload(thumbnailFile, "thumbnails");
            String contentImageUrl = s3Service.upload(contentImageFile, "contents");

            ProductRequestDto dtoWithImages = productRequestDto.toBuilder()
                    .thumbnailUrl(thumbnailUrl)
                    .contentImages(contentImageUrl)
                    .build();


            ProductResponseDto.from(productRepository.save(dtoWithImages.toEntity()));
        } catch (IOException e) {
            throw new BaseException(BaseResponseStatus.S3_UPLOAD_FAIL);
        }
    }*/

    //  판매 중인 상품 조회
    @Override
    public ProductResponseDto getSellingProduct(String productCode) {
        Product product = productRepository.findByProductCodeAndIsDeletedFalse(productCode)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_PRODUCT));

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

        product.update(productRequestDto);
        ProductResponseDto.from(productRepository.save(product));
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
