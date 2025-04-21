package com.example.shinsekai.batch.PurchaseWeeklyAggregation.infrastructure;

import com.example.shinsekai.batch.PurchaseWeeklyAggregation.domain.PurchaseWeeklyAggregation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseWeeklyAggregationRepository extends JpaRepository<PurchaseWeeklyAggregation,Long> {
}
