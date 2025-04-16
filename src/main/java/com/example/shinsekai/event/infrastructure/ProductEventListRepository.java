package com.example.shinsekai.event.infrastructure;

import com.example.shinsekai.event.entity.ProductEventList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;

public interface ProductEventListRepository extends JpaRepository<ProductEventList, Long> {
    List<ProductEventList> findAllByEventId(Integer eventId);
}
