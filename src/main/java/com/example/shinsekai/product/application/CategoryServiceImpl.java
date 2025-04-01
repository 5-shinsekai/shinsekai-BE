package com.example.shinsekai.product.application;

import com.example.shinsekai.common.entity.BaseResponseStatus;
import com.example.shinsekai.common.exception.BaseException;
import com.example.shinsekai.product.dto.in.*;
import com.example.shinsekai.product.dto.out.MainCategoryResponseDto;
import com.example.shinsekai.product.entity.category.MainCategory;
import com.example.shinsekai.product.entity.category.SubCategory;
import com.example.shinsekai.product.infrastructure.MainCategoryRepository;
import com.example.shinsekai.product.infrastructure.SubCategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final MainCategoryRepository mainCategoryRepository;
    private final SubCategoryRepository subCategoryRepository;

    @Override
    @Transactional
    public List<MainCategoryResponseDto> getAllMainCategory() {
        return mainCategoryRepository.findAll(Sort.by(Sort.Order.asc("name")))
                .stream()
                .map(MainCategoryResponseDto::from)
                .toList();
    }

    @Override
    @Transactional
    public void createMainCategory(MainCategoryCreateRequestDto mainCategoryCreateRequestDto) {
        try {
            mainCategoryRepository.save(mainCategoryCreateRequestDto.toEntity());
        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.INVALID_INPUT);
        }
    }

    @Override
    @Transactional
    public void softDeleteMainCategory(Long categoryId) {
        MainCategory mainCategory = mainCategoryRepository.findById(categoryId).orElseThrow(
                () -> new BaseException(BaseResponseStatus.NO_EXIST_PRODUCT)
        );
        mainCategory.setDeleted();
    }

    @Override
    @Transactional
    public void updateMainCategory(MainCategoryUpdateRequestDto mainCategoryUpdateRequestDto) {
        MainCategory mainCategory = mainCategoryRepository.findByIdAndIsDeletedFalse(mainCategoryUpdateRequestDto.getId()).orElseThrow(
                () -> new BaseException(BaseResponseStatus.NO_EXIST_PRODUCT)
        );

        MainCategory updateMainCategory = mainCategoryUpdateRequestDto.toEntity(mainCategory);

        try {
            mainCategoryRepository.save(updateMainCategory);
        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.INVALID_INPUT);
        }
    }

    @Override
    @Transactional
    public List<CategoryResponseDto> getAllSubCategory(Long categoryId) {
        return subCategoryRepository.findAllByMainCategoryIdAndIsDeletedFalse(categoryId, Sort.by(Sort.Order.asc("name")))
                .stream().map(CategoryResponseDto::from).toList();
    }

    @Override
    @Transactional
    public void createSubCategory(SubCategoryCreateRequestDto subCategoryCreateRequestDto) {
        if(!mainCategoryRepository.existsById(subCategoryCreateRequestDto.getMainCategoryId()))
            throw new BaseException(BaseResponseStatus.NO_EXIST_CATEGORY);

        try {
            subCategoryRepository.save(subCategoryCreateRequestDto.toEntity());
        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.INVALID_INPUT);
        }
    }

    @Override
    @Transactional
    public void softDeleteSubCategory(Long categoryId) {
        SubCategory subCategory = subCategoryRepository.findById(categoryId).orElseThrow(
                () -> new BaseException(BaseResponseStatus.NO_EXIST_CATEGORY)
        );
        subCategory.setDeleted();
    }

    @Override
    @Transactional
    public void updateSubCategory(SubCategoryUpdateRequestDto subCategoryUpdateRequestDto) {
        SubCategory subCategory = subCategoryRepository.findByIdAndIsDeletedFalse(subCategoryUpdateRequestDto.getId()).orElseThrow(
                () -> new BaseException(BaseResponseStatus.NO_EXIST_CATEGORY)
        );

        if(subCategoryUpdateRequestDto.getMainCategoryId() != null
                && !mainCategoryRepository.existsById(subCategoryUpdateRequestDto.getMainCategoryId()))
            throw new BaseException(BaseResponseStatus.NO_EXIST_CATEGORY);

        SubCategory updateSubCategory = subCategoryUpdateRequestDto.toEntity(subCategory);

        try{
            subCategoryRepository.save(updateSubCategory);
        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.INVALID_INPUT);
        }
    }
}