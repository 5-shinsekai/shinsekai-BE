package com.example.shinsekai.category.infrastructure;

import com.example.shinsekai.category.entity.PriceRange;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceRangeRepository extends JpaRepository<PriceRange, Integer> {
}
