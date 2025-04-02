package com.example.shinsekai.product.infrastructure;

import com.example.shinsekai.product.entity.category.MainCategory;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface  MainCategoryRepository extends JpaRepository<MainCategory, Long> {
    Optional<MainCategory> findByIdAndIsDeletedFalse(Long id);
    List<MainCategory> findAllByIsDeletedFalse(Sort sort);
}
