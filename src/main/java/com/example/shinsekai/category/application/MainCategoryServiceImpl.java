package com.example.shinsekai.category.application;

import com.example.shinsekai.category.dto.in.MainCategoryCreateRequestDto;
import com.example.shinsekai.category.dto.in.MainCategoryUpdateRequestDto;
import com.example.shinsekai.category.dto.out.CommonFilterItemDto;
import com.example.shinsekai.category.infrastructure.PriceRangeRepository;
import com.example.shinsekai.category.infrastructure.ProductCategoryListCustomRepoImpl;
import com.example.shinsekai.common.config.CategoryFilterConfig;
import com.example.shinsekai.common.entity.BaseResponseStatus;
import com.example.shinsekai.common.enums.FilterType;
import com.example.shinsekai.common.exception.BaseException;
import com.example.shinsekai.category.dto.out.CategoryFilterResponseDto;
import com.example.shinsekai.category.dto.out.SubCategoryResponseDto;
import com.example.shinsekai.category.dto.out.MainCategoryResponseDto;
import com.example.shinsekai.category.entity.MainCategory;
import com.example.shinsekai.category.infrastructure.MainCategoryRepository;
import com.example.shinsekai.category.infrastructure.SubCategoryRepository;
import com.example.shinsekai.season.infrastructure.SeasonRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Service
public class MainCategoryServiceImpl implements MainCategoryService {

    private final MainCategoryRepository mainCategoryRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final SeasonRepository seasonRepository;
    private final PriceRangeRepository priceRangeRepository;
    private final ProductCategoryListCustomRepoImpl productCategoryListCustomRepository;

    private final CategoryFilterConfig categoryFilterConfig;

    @Override
    @Transactional
    public List<MainCategoryResponseDto> getAllMainCategory() {
        return mainCategoryRepository.findAllByIsDeletedFalse(Sort.by(Sort.Order.asc("name")))
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
    public CategoryFilterResponseDto getCategoryFilter(Long mainCategoryId) {

//        Set<FilterType> filterTypes = categoryFilterConfig.getFilterTypesForCategory(mainCategoryId);

        List<SubCategoryResponseDto> subCategories = subCategoryRepository
                .findAllByMainCategoryIdAndIsDeletedFalse(mainCategoryId, Sort.by(Sort.Order.asc("name")))
                .stream().map(SubCategoryResponseDto::from).toList();

        List<CommonFilterItemDto> seasons = seasonRepository.findByEndDateAfter(LocalDate.now())
                .stream().map(CommonFilterItemDto::from).toList();

//        List<CommonFilterItemDto> sizes = filterTypes.contains(FilterType.SIZE)
//                ? Arrays.stream(SizeType.values()).map(CommonFilterItemDto::from).toList()
//                : List.of();

        List<CommonFilterItemDto> sizes = productCategoryListCustomRepository.findSizesByMainCategory(mainCategoryId)
                .stream().map(CommonFilterItemDto::from).toList();

//        List<CommonFilterItemDto> colors = filterTypes.contains(FilterType.COLOR)
//                ? Arrays.stream(ColorType.values()).map(CommonFilterItemDto::from).toList()
//                : List.of();

        List<CommonFilterItemDto> colors = productCategoryListCustomRepository.findColorsByMainCategory(mainCategoryId)
                .stream().map(CommonFilterItemDto::from).toList();

        List<CommonFilterItemDto> priceRange = priceRangeRepository.findAll()
                .stream().map(CommonFilterItemDto::from)
                .toList();

        return CategoryFilterResponseDto.builder()
                .subCategories(subCategories)
                .seasons(seasons)
                .sizes(sizes)
                .colors(colors)
                .priceRanges(priceRange)
                .build();
    }
}