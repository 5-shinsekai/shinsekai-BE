package com.example.shinsekai.card.application;

import com.example.shinsekai.card.dto.in.MemberStarbucksListDto;
import com.example.shinsekai.card.dto.in.StarbucksCardRequestDto;
import com.example.shinsekai.card.dto.in.UseStarbucksCardRequestDto;
import com.example.shinsekai.card.dto.out.StarbucksCardResponseDto;
import com.example.shinsekai.card.entity.MemberStarbucksCardList;
import com.example.shinsekai.card.entity.StarbucksCard;
import com.example.shinsekai.card.infrastructure.MemberStarbucksListRepository;
import com.example.shinsekai.card.infrastructure.StarbucksCardRepository;
import com.example.shinsekai.common.entity.BaseResponseStatus;
import com.example.shinsekai.common.exception.BaseException;
import com.example.shinsekai.common.jwt.JwtTokenProvider;
import io.swagger.v3.oas.annotations.Operation;
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

    //해당 회원의 활성화된 카드 조회
    @Override
    public List<StarbucksCardResponseDto> getActiveStarbucksCards(String memberUuid) {
        return memberStarbucksListRepository.findMemberStarbucksCardListByMemberUuidAndActiveIsTrue(memberUuid).stream()
                .map(memberStarbucksCardLists -> StarbucksCardResponseDto.from(
                                memberStarbucksCardLists.getStarbucksCard(),
                                memberStarbucksCardLists.getMemberStarbucksCardUuid()
                        )
                ).toList();
    }

    @Override
    @Transactional
    public void createStarbucksCard(StarbucksCardRequestDto starbucksCardDto) {
        memberStarbucksListRepository.save(MemberStarbucksListDto.builder()
                .memberUuid(starbucksCardDto.getMemberUuid())
                .build()
                .toEntity( starbucksCardRepository.save(starbucksCardDto.toEntity()) )
        );
    }

    @Override
    @Transactional
    public void deleteStarbucksCard(MemberStarbucksListDto memberStarbucksCardsListDto) {
        MemberStarbucksCardList memberStarbucksCardList = memberStarbucksListRepository
                .findMemberStarbucksCardListByMemberStarbucksCardUuidAndMemberUuidAndActiveIsTrue(
                        memberStarbucksCardsListDto.getMemberStarbucksCardUuid(), memberStarbucksCardsListDto.getMemberUuid()
                ).orElseThrow(() -> new BaseException(BaseResponseStatus.NO_DELETE_STARBUCKS_CARD));

        memberStarbucksCardList.softDelete();
    }
    /*
     * Todo :
     * 스타벅스 카드 결제 시 금액 차감
     * 시나리오상 존재(외부인증 X)해서 결제 내역 따로 저장 X
     * 잔액만 관리
     */
    @Override
    @Transactional
    public void useRemainAmount(UseStarbucksCardRequestDto useStarbucksCardRequestDto) {
        StarbucksCard starbucksCard = memberStarbucksListRepository.findMemberStarbucksCardListByMemberStarbucksCardUuidAndMemberUuidAndActiveIsTrue(
                useStarbucksCardRequestDto.getMemberStarbucksCardUuid(), useStarbucksCardRequestDto.getMemberUuid()
        ).orElseThrow(()->new BaseException(BaseResponseStatus.NO_EXIST_STARBUCKS_CARD)).getStarbucksCard();


        //잔액 2차 검증
        if(starbucksCard.getRemainAmount() < useStarbucksCardRequestDto.getPrice())
            throw new BaseException(BaseResponseStatus.NO_CHARGE_STARBUCKS_CARD);

        //잔액 차감
        starbucksCard.useRemainAmount(useStarbucksCardRequestDto.getPrice());
    }
}

