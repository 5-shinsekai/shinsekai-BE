package com.example.shinsekai.event.infrastructure;

import com.example.shinsekai.event.entity.ProductEventList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductEventListRepository extends JpaRepository<ProductEventList, Long> {
}
