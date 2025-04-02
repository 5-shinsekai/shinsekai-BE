package com.example.shinsekai.option.infrastructure;

import com.example.shinsekai.option.entity.ProductOptionList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductOptionListRepository extends JpaRepository<ProductOptionList, Long> {
}