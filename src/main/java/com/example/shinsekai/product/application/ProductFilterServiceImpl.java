package com.example.shinsekai.product.application;

import com.example.shinsekai.product.infrastructure.ProductFilterRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductFilterServiceImpl implements ProductFilterService {

    private final ProductFilterRepositoryCustom productFilterRepository;

    @Override
    public Page<String> filterProducts(Long mainCategoryId, List<Long> subCategoryIds, List<Integer> seasonIds,
                                       List<Long> sizeIds, Integer priceRangeId, Pageable pageable) {
        return productFilterRepository.filterProductCodes(
                mainCategoryId, subCategoryIds, seasonIds, sizeIds, priceRangeId, pageable);
    }
}
