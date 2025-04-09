package com.example.shinsekai.option.infrastructure;

import com.example.shinsekai.option.entity.ProductOptionList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductOptionListRepository extends JpaRepository<ProductOptionList, Long> {
    Optional<ProductOptionList> findById(Long id);

    boolean existsByProductCodeAndColorIdAndSizeId(String productCode, long colorId, long sizeId);

    List<ProductOptionList> findAllByProductCode(String productCode);
}