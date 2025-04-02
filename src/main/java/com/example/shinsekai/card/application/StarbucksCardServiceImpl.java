package com.example.shinsekai.card.application;

import com.example.shinsekai.card.dto.in.StarbucksCardRequestDto;
import com.example.shinsekai.card.dto.out.StarbucksCardResponseDto;
import com.example.shinsekai.card.entity.MemberStarbucksCardList;
import com.example.shinsekai.card.entity.StarbucksCard;
import com.example.shinsekai.card.infrastructure.MemberStarbucksListRepository;
import com.example.shinsekai.card.infrastructure.StarbucksCardRepository;
import com.example.shinsekai.common.entity.BaseResponseStatus;
import com.example.shinsekai.common.exception.BaseException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class StarbucksCardServiceImpl implements StarbucksCardService {

    private final StarbucksCardRepository starbucksCardRepository;
    private final MemberStarbucksListRepository memberStarbucksListRepository;

    @Override
    public List<StarbucksCardResponseDto> getStarbucksCard(String memberUuid) {

        return memberStarbucksListRepository.findMemberStarbucksCardListByMemberUuidAndActive(memberUuid, true).stream()
                .map(memberStarbucksCardLists -> StarbucksCardResponseDto.from(
                        memberStarbucksCardLists.getStarbucksCard(),
                        memberStarbucksCardLists.getMemberStarbucksCardUuid()
                    )
                ).toList();
    }

    @Override
    @Transactional
    public void createStarbucksCard(String memberUuid, StarbucksCardRequestDto starbucksCardDto) {

        try{
            String starbucksCardUuid = UUID.randomUUID().toString();
            double remainAmount = 50000;
            String cardImgUrl = "/card/img/starbucksCard.png";
            String carDescription = "Starbucks Card";

            StarbucksCard starbucksCard = starbucksCardDto.toEntity(starbucksCardUuid, remainAmount, cardImgUrl, carDescription);

            saveStarbucksCard(starbucksCard);
            saveMemberStarbucksCardList(memberUuid, starbucksCard);

        }catch (Exception e){
            throw new BaseException(BaseResponseStatus.NO_CREATION_STARBUCKS_CARD);
        }

    }


    @Override
    public void saveStarbucksCard(StarbucksCard starbucksCard) {
        starbucksCardRepository.save(starbucksCard);
    }

    @Override
    public void saveMemberStarbucksCardList(String memberUuid, StarbucksCard starbucksCard) {

        String memberStarbucksCardUuid = UUID.randomUUID().toString();

        memberStarbucksListRepository.save(MemberStarbucksCardList.builder()
                        .memberStarbucksCardUuid(memberStarbucksCardUuid)
                        .memberUuid(memberUuid)
                        .starbucksCard(starbucksCard)
                        .active(true)
                        .build()
        );
    }

    @Override
    @Transactional
    public void deleteStarbucksCard(String starbucksCardUuid) {
        try {
            Optional<MemberStarbucksCardList> memberStarbucksCardList
                    = memberStarbucksListRepository.findMemberStarbucksCardListByMemberStarbucksCardUuid(starbucksCardUuid);
            memberStarbucksCardList.ifPresent(MemberStarbucksCardList::changeActive);
        }catch (Exception e){
            throw new BaseException(BaseResponseStatus.NO_DELETE_STARBUCKS_CARD);
        }
    }

}

