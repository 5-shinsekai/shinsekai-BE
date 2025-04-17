package com.example.shinsekai.category.application;

import com.example.shinsekai.category.dto.in.MainCategoryCreateRequestDto;
import com.example.shinsekai.category.dto.in.MainCategoryUpdateRequestDto;
import com.example.shinsekai.category.dto.out.MainCategoryResponseDto;
import com.example.shinsekai.category.entity.MainCategory;
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
public class MainCategoryServiceImpl implements MainCategoryService {

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
        mainCategoryRepository.save(mainCategoryCreateRequestDto.toEntity());
    }

    @Override
    @Transactional
    public void deleteMainCategory(Long categoryId) {
        MainCategory mainCategory = mainCategoryRepository.findById(categoryId).orElseThrow(
                () -> new BaseException(BaseResponseStatus.NO_EXIST_PRODUCT)
        );

        mainCategoryRepository.delete(mainCategory);
    }

    @Override
    @Transactional
    public void updateMainCategory(MainCategoryUpdateRequestDto mainCategoryUpdateRequestDto) {
        MainCategory mainCategory = mainCategoryRepository.findById(mainCategoryUpdateRequestDto.getId())
                .orElseThrow(
                        () -> new BaseException(BaseResponseStatus.NO_EXIST_PRODUCT)
                );

        mainCategoryRepository.save( mainCategoryUpdateRequestDto.toEntity(mainCategory));
    }
}