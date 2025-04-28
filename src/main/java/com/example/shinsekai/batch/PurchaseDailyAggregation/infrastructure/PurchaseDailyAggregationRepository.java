package com.example.shinsekai.batch.PurchaseDailyAggregation.infrastructure;

import com.example.shinsekai.batch.PurchaseDailyAggregation.domain.PurchaseDailyAggregation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseDailyAggregationRepository extends JpaRepository<PurchaseDailyAggregation, Long> {
}
