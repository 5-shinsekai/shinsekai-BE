package com.example.shinsekai.product.application;

import com.example.shinsekai.common.entity.BaseResponseStatus;
import com.example.shinsekai.common.exception.BaseException;
import com.example.shinsekai.product.dto.out.MainCategorysGetResponseDto;
import com.example.shinsekai.product.entity.category.MainCategory;
import com.example.shinsekai.product.infrastructure.MainCategoryRepository;
import com.example.shinsekai.product.vo.out.MainCategorysGetResponseVo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final MainCategoryRepository mainCategoryRepository;

    @Override
    public List<MainCategorysGetResponseVo> getMainCategorysName() {
        List<MainCategory> mainCategoryList = mainCategoryRepository.findAll(Sort.by(Sort.Order.asc("id")));

        if (mainCategoryList.isEmpty()) {
            throw new BaseException(BaseResponseStatus.NO_EXIST_CATEGORY);
        }

        return mainCategoryList.stream()
                .map(MainCategorysGetResponseDto::from)
                .map(MainCategorysGetResponseDto::toVo)
                .toList();

    }
}