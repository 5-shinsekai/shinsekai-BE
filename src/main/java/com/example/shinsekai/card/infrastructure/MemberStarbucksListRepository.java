package com.example.shinsekai.card.infrastructure;

import com.example.shinsekai.card.entity.MemberStarbucksCardList;
import com.example.shinsekai.card.entity.StarbucksCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberStarbucksListRepository extends JpaRepository<MemberStarbucksCardList,Long> {

    List<MemberStarbucksCardList> findMemberStarbucksCardListByMemberUuidAndActiveIsTrue(String memberUuid);
    Optional<MemberStarbucksCardList> findMemberStarbucksCardListByMemberStarbucksCardUuidAndMemberUuid(String memberStarbucksCardListUuid, String memberUuid);
}
