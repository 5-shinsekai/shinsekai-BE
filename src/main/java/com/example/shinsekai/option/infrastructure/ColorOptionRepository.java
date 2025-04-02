package com.example.shinsekai.option.infrastructure;

import com.example.shinsekai.option.entity.Color;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColorOptionRepository extends JpaRepository<Color, Integer> {
}
