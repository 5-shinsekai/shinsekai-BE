package com.example.shinsekai.product.infrastructure;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductSearchRepositoryCustom {
    Page<String> searchByProductName(String keyword, Pageable pageable);
}
