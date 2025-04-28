package com.example.shinsekai.purchase.infrastructure;

import com.example.shinsekai.purchase.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    Optional<Purchase> findByPurchaseCodeAndMemberUuid(String purchaseCode, String memberUuid);

    List<Purchase> findByMemberUuid(String memberUuid);

    List<Purchase> findByMemberUuidAndCreatedAtBetween(String memberUuid, LocalDateTime startDate, LocalDateTime endDate);

}
