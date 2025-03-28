package com.example.shinsekai.product.infrastructure;

import com.example.shinsekai.product.entity.option.ProductOptionList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOptionListRepository extends JpaRepository<ProductOptionList, Long> {
}