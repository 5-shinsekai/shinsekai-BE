package com.example.shinsekai.option.infrastructure;

import com.example.shinsekai.option.entity.ProductOptionList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductOptionListRepository extends JpaRepository<ProductOptionList, Long> {
    List<ProductOptionList> findByProductCode(String productCode);

    boolean existsByProductCodeAndColorIdAndSizeId(String productCode, long colorId, long sizeId);
}