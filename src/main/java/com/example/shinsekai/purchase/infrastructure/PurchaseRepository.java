package com.example.shinsekai.purchase.infrastructure;

import com.example.shinsekai.purchase.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    Optional<Purchase> findByPurchaseCodeAndMemberUuid(String purchaseCode, String memberUuid);
}
