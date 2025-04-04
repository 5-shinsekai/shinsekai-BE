package com.example.shinsekai.season.infrastructure;

import com.example.shinsekai.season.entity.Season;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface SeasonRepository extends JpaRepository<Season, Integer> {
    List<Season> findByEndDateAfter(LocalDate today);
}
