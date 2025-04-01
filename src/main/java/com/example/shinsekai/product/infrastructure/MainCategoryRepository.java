package com.example.shinsekai.product.infrastructure;

import com.example.shinsekai.product.entity.category.MainCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MainCategoryRepository extends JpaRepository<MainCategory, Long> {
    Optional<MainCategory> findByIdAndIsDeletedFalse(Long id);
}
