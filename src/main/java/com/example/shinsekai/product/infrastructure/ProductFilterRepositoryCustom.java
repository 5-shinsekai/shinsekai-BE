package com.example.shinsekai.product.infrastructure;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductFilterRepositoryCustom {
    Page<String> filterProductCodes(
            Long mainCategoryId,
            List<Long> subCategoryIds,
            List<Integer> seasonIds,
            List<Long> sizeIds,
            Integer priceRangeId,
            Pageable pageable);
}
