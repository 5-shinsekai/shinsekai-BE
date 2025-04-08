package com.example.shinsekai.category.infrastructure;

import com.example.shinsekai.category.entity.SubCategory;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {
    List<SubCategory> findAllByMainCategoryId (Long categoryId, Sort sort);
}
