package com.example.shinsekai.product.infrastructure;

import com.example.shinsekai.product.entity.category.SubCategory;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {
    List<SubCategory> findAllByMainCategoryIdAndIsDeletedFalse (Long categoryId, Sort sort);
    Optional<SubCategory> findByIdAndIsDeletedFalse(Long id);
}
