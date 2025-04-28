package com.example.shinsekai.card.application;

import com.example.shinsekai.card.dto.in.MemberStarbucksListDto;
import com.example.shinsekai.card.dto.in.StarbucksCardRequestDto;
import com.example.shinsekai.card.dto.in.TransferStarbucksCardDto;
import com.example.shinsekai.card.dto.in.UseStarbucksCardRequestDto;
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

    //활성화된 단일 카드 조회


    @Override
    public StarbucksCardResponseDto getStarbucksCard(MemberStarbucksListDto dto) {

        return StarbucksCardResponseDto.from(
                memberStarbucksListRepository.findByMemberStarbucksCardUuidAndMemberUuidAndActiveIsTrue(
                                dto.getMemberStarbucksCardUuid(), dto.getMemberUuid()
                        ).orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_STARBUCKS_CARD))
                        .getStarbucksCard(), dto.getMemberStarbucksCardUuid()
        );
    }

    @Override
    @Transactional
    public void createStarbucksCard(StarbucksCardRequestDto starbucksCardDto) {
        memberStarbucksListRepository.save(MemberStarbucksListDto.builder()
                .memberUuid(starbucksCardDto.getMemberUuid())
                .build()
                .toEntity(starbucksCardRepository.save(starbucksCardDto.toEntity()))
        );
    }

    @Override
    @Transactional
    public void deleteStarbucksCard(MemberStarbucksListDto memberStarbucksCardsListDto) {
        MemberStarbucksCardList memberStarbucksCardList = memberStarbucksListRepository
                .findByMemberStarbucksCardUuidAndMemberUuidAndActiveIsTrue(
                        memberStarbucksCardsListDto.getMemberStarbucksCardUuid(), memberStarbucksCardsListDto.getMemberUuid()
                ).orElseThrow(() -> new BaseException(BaseResponseStatus.NO_DELETE_STARBUCKS_CARD));

        //잔액 존재하는지 검사
        if (memberStarbucksCardList.getStarbucksCard().getRemainAmount() > 0)
            throw new BaseException(BaseResponseStatus.NO_DELETE_STARBUCKS_CARD);

        memberStarbucksCardList.softDelete();
    }

    /*
     * Todo :
     *  스타벅스 카드 결제 시 금액 차감
     *  시나리오상 존재(외부인증 X)해서 결제 내역 따로 저장 X
     *  잔액만 관리
     */
    @Override
    @Transactional
    public void useRemainAmount(UseStarbucksCardRequestDto useStarbucksCardRequestDto) {

        //결제 하는 금액 검사
        if (useStarbucksCardRequestDto.getPrice() < 0)
            throw new BaseException(BaseResponseStatus.INVALID_INPUT);

        StarbucksCard starbucksCard = memberStarbucksListRepository.findByMemberStarbucksCardUuidAndMemberUuidAndActiveIsTrue(
                useStarbucksCardRequestDto.getMemberStarbucksCardUuid(), useStarbucksCardRequestDto.getMemberUuid()
        ).orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_STARBUCKS_CARD)).getStarbucksCard();


        //잔액 2차 검증
        if (starbucksCard.getRemainAmount() < useStarbucksCardRequestDto.getPrice())
            throw new BaseException(BaseResponseStatus.NO_CHARGE_STARBUCKS_CARD);

        //잔액 차감
        starbucksCard.useRemainAmount(useStarbucksCardRequestDto.getPrice());
    }

    @Override
    @Transactional
    public void chargeRemainAmount(UseStarbucksCardRequestDto useStarbucksCardRequestDto) {
        //충전 금액 1000원 이상
        if (useStarbucksCardRequestDto.getPrice() < 1000)
            throw new BaseException(BaseResponseStatus.NO_CHARGE_STARBUCKS_CARD);

        memberStarbucksListRepository.findByMemberStarbucksCardUuidAndMemberUuidAndActiveIsTrue(
                        useStarbucksCardRequestDto.getMemberStarbucksCardUuid(), useStarbucksCardRequestDto.getMemberUuid()
                ).orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_STARBUCKS_CARD))
                .getStarbucksCard().chargeRemainAmount(useStarbucksCardRequestDto.getPrice());
    }

    @Override
    @Transactional
    public void transferRemainAmount(TransferStarbucksCardDto dto) {
        StarbucksCard source =
                memberStarbucksListRepository.findByMemberStarbucksCardUuidAndMemberUuidAndActiveIsTrue(
                        dto.getSourceMemberStarbucksCardUuid(), dto.getMemeberUuid()
                ).orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_STARBUCKS_CARD)).getStarbucksCard();

        StarbucksCard target =
                memberStarbucksListRepository.findByMemberStarbucksCardUuidAndMemberUuidAndActiveIsTrue(
                        dto.getTargetMemberStarbucksCardUuid(), dto.getMemeberUuid()
                ).orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_STARBUCKS_CARD)).getStarbucksCard();

        //이전 할 잔액 유효성 검사
        if (source.getRemainAmount() < dto.getRemainAmount())
            throw new BaseException(BaseResponseStatus.NO_CHARGE_STARBUCKS_CARD);

        target.chargeRemainAmount(dto.getRemainAmount());
        source.useRemainAmount(dto.getRemainAmount());
    }


}

