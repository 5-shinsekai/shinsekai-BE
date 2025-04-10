package com.example.shinsekai.product.application;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductFilterService {

    Page<String> filterProducts(Long mainCategoryId, List<Long> subCategoryIds, List<Integer> seasonIds,
                                List<Long> sizeIds, String priceRange, Pageable pageable);
}
