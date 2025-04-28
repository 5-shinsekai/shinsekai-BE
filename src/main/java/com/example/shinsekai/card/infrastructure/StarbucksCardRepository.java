package com.example.shinsekai.card.infrastructure;

import com.example.shinsekai.card.entity.StarbucksCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface StarbucksCardRepository extends JpaRepository<StarbucksCard, Long> {
}
