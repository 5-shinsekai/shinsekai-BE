package com.example.shinsekai.batch.bestProduct.infrastructure;

import com.example.shinsekai.batch.bestProduct.domain.BestProduct;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BestProductRepository extends JpaRepository<BestProduct, Long> {
    boolean existsByProductCode(String productCode);

    @Query("SELECT b.productCode FROM BestProduct b " +
            "WHERE b.mainCategoryId = :mainCategoryId " +
            "ORDER BY b.productRank ASC")
    List<String> findProductCodesByMainCategoryOrderedByRank (@Param("mainCategoryId") Long mainCategoryId);
}
