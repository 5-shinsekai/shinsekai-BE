package com.example.shinsekai.batch.bestProduct.infrastructure;

import com.example.shinsekai.batch.bestProduct.domain.ProductScore;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductScoreRepository extends JpaRepository<ProductScore, Long> {
}
