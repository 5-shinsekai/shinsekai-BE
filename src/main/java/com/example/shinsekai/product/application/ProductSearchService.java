package com.example.shinsekai.product.application;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductSearchService {

    Page<String> searchProducts(Long mainCategoryId, List<Long> subCategoryIds, List<Integer> seasonIds,
                                List<Long> sizeIds, String priceRange, Pageable pageable);
}
