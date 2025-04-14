package com.example.shinsekai.product.application;

import com.example.shinsekai.product.infrastructure.ProductSearchRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductSearchServiceImpl implements ProductSearchService {

    private final ProductSearchRepositoryImpl productSearchRepository;

    @Override
    public Page<String> searchByKeyword(String keyword, Pageable pageable) {
        return productSearchRepository.searchByProductName(keyword,pageable);
    }
}
