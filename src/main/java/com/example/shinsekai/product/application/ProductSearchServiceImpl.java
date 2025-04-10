package com.example.shinsekai.product.application;

import com.example.shinsekai.product.infrastructure.ProductSearchRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductSearchServiceImpl implements ProductSearchService {

    private final ProductSearchRepositoryCustom productSearchRepository;

    @Override
    public Page<String> searchProducts(Long mainCategoryId, List<Long> subCategoryIds, List<Integer> seasonIds,
                                       List<Long> sizeIds, String priceRange, Pageable pageable) {
        return productSearchRepository.searchProductCodes(
                mainCategoryId, subCategoryIds, seasonIds, sizeIds, priceRange, pageable);
    }
}
