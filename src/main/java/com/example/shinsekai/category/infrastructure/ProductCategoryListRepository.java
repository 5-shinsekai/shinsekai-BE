package com.example.shinsekai.category.infrastructure;

import com.example.shinsekai.category.entity.ProductCategoryList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductCategoryListRepository extends JpaRepository<ProductCategoryList, Long> {
    Optional<ProductCategoryList> findByProductCode(String productCode);
}
