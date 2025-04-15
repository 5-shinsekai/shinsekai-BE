package com.example.shinsekai.purchase.infrastructure;

import com.example.shinsekai.purchase.entity.PurchaseProductList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PurchaseProductListRepository extends JpaRepository<PurchaseProductList, Long> {
    List<PurchaseProductList> findByPurchaseCode(String purchaseCode);
}
