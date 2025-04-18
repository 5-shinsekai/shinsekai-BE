package com.example.shinsekai.batch.bestProduct.infrastructure;

import com.example.shinsekai.batch.bestProduct.domain.purchaseDailyAggregation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface purchaseDailyAggregationRepository extends JpaRepository<purchaseDailyAggregation,Long> {
}
