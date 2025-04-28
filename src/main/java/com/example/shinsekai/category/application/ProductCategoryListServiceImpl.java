package com.example.shinsekai.category.application;

import com.example.shinsekai.category.dto.in.*;
import com.example.shinsekai.category.dto.out.ProductCategoryListGetResponseDto;
import com.example.shinsekai.category.entity.ProductCategoryList;
import com.example.shinsekai.category.infrastructure.ProductCategoryListRepository;
import com.example.shinsekai.common.entity.BaseResponseStatus;
import com.example.shinsekai.common.exception.BaseException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductCategoryListServiceImpl implements ProductCategoryListService {

    private final ProductCategoryListRepository productCategoryListRepository;

    @Override
    @Transactional
    public void createProductCategoryList(ProductCategoryListCreateRequestDto productCategoryListCreateRequestDto) {
        try {
            productCategoryListRepository.save(productCategoryListCreateRequestDto.toEntity());
        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.FAILED_TO_SAVE);
        }
    }

    @Override
    public ProductCategoryListGetResponseDto getProductCategoryList(
            ProductCategoryListGetRequestDto productCategoryListGetRequestDto) {
        return ProductCategoryListGetResponseDto.from(
                productCategoryListRepository.findById(productCategoryListGetRequestDto.getId())
                        .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_PRODUCT_CATEGORY))
        );
    }

    @Override
    public ProductCategoryListGetResponseDto getProductCategoryListByProductCode(
            ProductCategoryListGetByProductCodeRequestDto dto
    ) {
        return ProductCategoryListGetResponseDto.from(
                productCategoryListRepository.findByProductCode(dto.getProductCode())
                        .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_PRODUCT_CATEGORY)));
    }

    @Override
    @Transactional
    public void updateProductCategoryList(ProductCategoryListUpdateRequestDto productCategoryListUpdateRequestDto) {
        ProductCategoryList productCategoryList = productCategoryListRepository.findById(productCategoryListUpdateRequestDto.getId())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_PRODUCT_CATEGORY));

        productCategoryListRepository.save(productCategoryListUpdateRequestDto.toEntity(productCategoryList));
    }

    @Override
    @Transactional
    public void deleteProductCategoryList(ProductCategoryListDeleteRequestDto productCategoryListDeleteRequestDto) {
        productCategoryListRepository.deleteById(productCategoryListDeleteRequestDto.getId());
    }

    @Override
    @Transactional
    public void deleteAllProductCategoryList(List<ProductCategoryListDeleteRequestDto> productCategoryListDeleteRequestDtoLsit) {
        productCategoryListRepository.deleteAllById(productCategoryListDeleteRequestDtoLsit.stream()
                .map(ProductCategoryListDeleteRequestDto::getId).toList());
    }
}
