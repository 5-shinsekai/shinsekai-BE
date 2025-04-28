package com.example.shinsekai.product.infrastructure;

import com.example.shinsekai.product.entity.Product;
import com.example.shinsekai.product.entity.ProductStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByProductCode(String productCode);

    boolean existsByProductCode(String productCode);

    Optional<Product> findByProductCodeAndIsDeletedFalse(String productCode);

    Page<Product> findAllByIsDeletedFalseAndProductStatus(ProductStatus status, Pageable pageable);

    @Query("SELECT p.productCode FROM Product p WHERE p.isDeleted = false AND p.productStatus = 'SELLING'")
    Page<String> findProductCodesByIsDeletedFalseAndProductStatusSelling(Pageable pageable);


}