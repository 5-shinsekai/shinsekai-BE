package com.example.shinsekai.common.config;

import com.example.shinsekai.common.enums.FilterType;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

@Component
public class CategoryFilterConfig {
    private static final Map<Long, Set<FilterType>> CATEGORY_FILTERS = Map.of(
            1L, Set.of(     // 전체
                    FilterType.SEASON,
                    FilterType.PRICE
            ),

            2L, Set.of(   // 텀블러/보온병
                    FilterType.SUB_CATEGORY,
                    FilterType.SEASON,
                    FilterType.SIZE,
                    FilterType.COLOR,
                    FilterType.PRICE
            ),

            3L, Set.of(   // 머그/컵
                    FilterType.SUB_CATEGORY,
                    FilterType.SEASON,
                    FilterType.SIZE,
                    FilterType.COLOR,
                    FilterType.PRICE
            ),

            4L, Set.of(   // 라이프스타일
                    FilterType.SEASON,
                    FilterType.PRICE
            ),

            5L, Set.of(   // 티/커피용품
                    FilterType.SEASON,
                    FilterType.SIZE,
                    FilterType.COLOR,
                    FilterType.PRICE
            ),

            6L, Set.of(   // 케이크
                    FilterType.SUB_CATEGORY,
                    FilterType.SEASON,
                    FilterType.PRICE
            ),

            7L, Set.of(   // 초콜릿/스낵
                    FilterType.SEASON,
                    FilterType.PRICE
            ),

            8L, Set.of(   // 세트
                    FilterType.SEASON,
                    FilterType.PRICE
            )
    );


    public Set<FilterType> getFilterTypesForCategory(Long mainCategoryId) {
        return CATEGORY_FILTERS.getOrDefault(mainCategoryId, Set.of());
    }
}
