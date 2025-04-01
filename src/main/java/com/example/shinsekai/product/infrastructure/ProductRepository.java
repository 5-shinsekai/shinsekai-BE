package com.example.shinsekai.product.infrastructure;

import com.example.shinsekai.product.entity.Product;
import com.example.shinsekai.product.entity.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByProductCode(String productCode);
    boolean existsByProductCode(String productCode);

    Optional<Product> findByProductCodeAndIsDeletedFalse(String productCode);

    Optional<Product> findByProductCodeAndIsDeletedTrue(String productCode);

    List<Product> findAllByIsDeletedFalse(); //find all 주의 (pageable)

    List<Product> findAllByIsDeletedFalseAndProductStatus(ProductStatus status);


}