package com.example.shinsekai.batch.PurchaseDailyAggregation.infrastructure;

import com.example.shinsekai.batch.PurchaseDailyAggregation.domain.purchaseDailyAggregation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface purchaseDailyAggregationRepository extends JpaRepository<purchaseDailyAggregation,Long> {
}
