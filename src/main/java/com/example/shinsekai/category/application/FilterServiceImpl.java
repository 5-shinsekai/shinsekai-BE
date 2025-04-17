package com.example.shinsekai.category.application;

import com.example.shinsekai.category.dto.out.CategoryFilterResponseDto;
import com.example.shinsekai.category.dto.out.CommonFilterItemDto;
import com.example.shinsekai.category.entity.FilterSummary;
import com.example.shinsekai.category.infrastructure.FilterSummaryRepository;
import com.example.shinsekai.category.infrastructure.PriceRangeRepository;
import com.example.shinsekai.season.infrastructure.SeasonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class FilterServiceImpl implements FilterService{

    private final FilterSummaryRepository filterSummaryRepository;
    private final SeasonRepository seasonRepository;
    private final PriceRangeRepository priceRangeRepository;

    @Override
    public CategoryFilterResponseDto getCategoryFilter(Long mainCategoryId) {
        List<CommonFilterItemDto> seasons = seasonRepository.findByEndDateAfter(LocalDate.now())
                .stream().map(CommonFilterItemDto::from).toList();

        List<FilterSummary> filters = filterSummaryRepository.findByMainCategoryIdAndFilterTypeIn(
                mainCategoryId, List.of("size", "color")
        );

        List<CommonFilterItemDto> sizes = filters.stream()
                .filter(f -> f.getFilterType().equals("size"))
                .map(CommonFilterItemDto::from)
                .toList();

        List<CommonFilterItemDto> colors = filters.stream()
                .filter(f -> f.getFilterType().equals("color"))
                .map(CommonFilterItemDto::from)
                .toList();


        List<CommonFilterItemDto> priceRange = priceRangeRepository.findAll()
                .stream().map(CommonFilterItemDto::from)
                .toList();

        return CategoryFilterResponseDto.builder()
                .seasons(seasons)
                .sizes(sizes)
                .colors(colors)
                .priceRanges(priceRange)
                .build();
    }
}
