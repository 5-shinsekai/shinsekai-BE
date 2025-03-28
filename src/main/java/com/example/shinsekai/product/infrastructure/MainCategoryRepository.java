package com.example.shinsekai.product.infrastructure;

import com.example.shinsekai.product.entity.category.MainCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MainCategoryRepository extends JpaRepository<MainCategory, Long> {
}
