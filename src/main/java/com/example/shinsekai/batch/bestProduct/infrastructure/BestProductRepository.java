package com.example.shinsekai.batch.bestProduct.infrastructure;

import com.example.shinsekai.batch.bestProduct.domain.BestProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BestProductRepository extends JpaRepository<BestProduct, Long> {
}
