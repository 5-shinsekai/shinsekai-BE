package com.example.shinsekai.card.infrastructure;

import com.example.shinsekai.card.entity.StarbucksCardKeyMap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StarbucksCardKeyMapRepository extends JpaRepository<StarbucksCardKeyMap, Long> {
    Optional<StarbucksCardKeyMap> findByCardNumberAndIsRegistered(String starbucksCardNumber, boolean registered);
}
