package com.example.shinsekai.product.application;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductSearchService {
    Page<String> searchByKeyword(String keyword, Pageable pageable);
}
