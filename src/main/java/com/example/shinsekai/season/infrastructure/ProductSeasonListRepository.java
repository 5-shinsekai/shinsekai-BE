package com.example.shinsekai.season.infrastructure;

import com.example.shinsekai.season.entity.ProductSeasonList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductSeasonListRepository extends JpaRepository<ProductSeasonList, Long> {
    List<ProductSeasonList> findAllBySeasonId(Long seasonId);
}
