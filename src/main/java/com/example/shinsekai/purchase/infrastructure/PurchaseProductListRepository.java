package com.example.shinsekai.purchase.infrastructure;

import com.example.shinsekai.purchase.entity.PurchaseProductList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseProductListRepository extends JpaRepository<PurchaseProductList, Long> {
}
