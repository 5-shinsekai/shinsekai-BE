package com.example.shinsekai.card.infrastructure;

import com.example.shinsekai.card.entity.MemberStarbucksCardList;
import com.example.shinsekai.card.entity.StarbucksCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StarbucksCardRepository extends JpaRepository<StarbucksCard, Long> {
}
