package com.example.shinsekai.batch.bestProduct.infrastructure;

import com.example.shinsekai.batch.bestProduct.domain.BestProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BestProductRepository extends JpaRepository<BestProduct, Long> {
    boolean existsByProductCode(String productCode);

    List<BestProduct> findByMainCategoryId(Long mainCategoryId);
}
