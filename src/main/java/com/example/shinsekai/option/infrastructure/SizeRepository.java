package com.example.shinsekai.option.infrastructure;

import com.example.shinsekai.option.entity.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SizeRepository extends JpaRepository<Size, Long> {
    Optional<Size> findBySizeName(String sizeName);
}
