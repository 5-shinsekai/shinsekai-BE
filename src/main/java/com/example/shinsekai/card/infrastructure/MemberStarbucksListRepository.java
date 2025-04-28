package com.example.shinsekai.card.infrastructure;

import com.example.shinsekai.card.entity.MemberStarbucksCardList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberStarbucksListRepository extends JpaRepository<MemberStarbucksCardList, Long> {

    List<MemberStarbucksCardList> findMemberStarbucksCardListByMemberUuidAndActiveIsTrue(String memberUuid);

    Optional<MemberStarbucksCardList> findByMemberStarbucksCardUuidAndMemberUuidAndActiveIsTrue(String memberStarbucksCardListUuid, String memberUuid);
}
