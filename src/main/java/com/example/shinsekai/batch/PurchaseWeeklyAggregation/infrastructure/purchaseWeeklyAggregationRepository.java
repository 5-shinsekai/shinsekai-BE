package com.example.shinsekai.batch.PurchaseWeeklyAggregation.infrastructure;

import com.example.shinsekai.batch.PurchaseWeeklyAggregation.domain.purchaseWeeklyAggregation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface purchaseWeeklyAggregationRepository extends JpaRepository<purchaseWeeklyAggregation,Long> {
}
