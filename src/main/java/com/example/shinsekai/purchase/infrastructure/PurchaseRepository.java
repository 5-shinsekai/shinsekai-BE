package com.example.shinsekai.purchase.infrastructure;

import com.example.shinsekai.purchase.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
}
