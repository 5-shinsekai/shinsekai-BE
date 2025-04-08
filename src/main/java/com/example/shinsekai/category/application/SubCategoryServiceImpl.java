package com.example.shinsekai.category.application;

import com.example.shinsekai.category.dto.in.SubCategoryCreateRequestDto;
import com.example.shinsekai.category.dto.in.SubCategoryUpdateRequestDto;
import com.example.shinsekai.category.dto.out.SubCategoryResponseDto;
import com.example.shinsekai.category.entity.SubCategory;
import com.example.shinsekai.category.infrastructure.MainCategoryRepository;
import com.example.shinsekai.category.infrastructure.SubCategoryRepository;
import com.example.shinsekai.common.entity.BaseResponseStatus;
import com.example.shinsekai.common.exception.BaseException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class SubCategoryServiceImpl implements SubCategoryService{

    private final SubCategoryRepository subCategoryRepository;
    private final MainCategoryRepository mainCategoryRepository;

    @Override
    @Transactional
    public List<SubCategoryResponseDto> getAllSubCategory(Long categoryId) {
        return subCategoryRepository.findAllByMainCategoryId(categoryId, Sort.by(Sort.Order.asc("name")))
                .stream().map(SubCategoryResponseDto::from).toList();
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
    public void deleteSubCategory(Long categoryId) {
        SubCategory subCategory = subCategoryRepository.findById(categoryId).orElseThrow(
                () -> new BaseException(BaseResponseStatus.NO_EXIST_CATEGORY)
        );

        subCategoryRepository.delete(subCategory);
    }

    @Override
    @Transactional
    public void updateSubCategory(SubCategoryUpdateRequestDto subCategoryUpdateRequestDto) {
        SubCategory subCategory = subCategoryRepository.findById(subCategoryUpdateRequestDto.getId())
                .orElseThrow(
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
