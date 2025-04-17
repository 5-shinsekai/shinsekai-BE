package com.example.shinsekai.category.infrastructure;

import com.example.shinsekai.category.entity.FilterSummary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FilterSummaryRepository extends JpaRepository<FilterSummary, Long> {
    List<FilterSummary> findByMainCategoryIdAndFilterTypeIn(Long mainCategoryId, List<String> filterTypes);
}
