package com.example.shinsekai.option.infrastructure;

import com.example.shinsekai.option.entity.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SizeOptionRepository extends JpaRepository<Size, Long> {
}
