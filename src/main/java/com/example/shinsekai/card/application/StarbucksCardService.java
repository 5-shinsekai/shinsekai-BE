package com.example.shinsekai.card.application;

import com.example.shinsekai.card.dto.in.MemberStarbucksListDto;
import com.example.shinsekai.card.dto.in.StarbucksCardRequestDto;
import com.example.shinsekai.card.dto.in.TransferStarbucksCardDto;
import com.example.shinsekai.card.dto.in.UseStarbucksCardRequestDto;
import com.example.shinsekai.card.dto.out.StarbucksCardResponseDto;

import java.util.List;

public interface StarbucksCardService {
    List<StarbucksCardResponseDto> getActiveStarbucksCards(String memberUuid);

    StarbucksCardResponseDto getStarbucksCard(MemberStarbucksListDto memberStarbucksCardDto);

    void createStarbucksCard(StarbucksCardRequestDto starbucksCardRequestDto);

    void deleteStarbucksCard(MemberStarbucksListDto memberStarbucksCardsListDto);

    void useRemainAmount(UseStarbucksCardRequestDto useStarbucksCardRequestDto);

    void chargeRemainAmount(UseStarbucksCardRequestDto useStarbucksCardRequestDto);

    void transferRemainAmount(TransferStarbucksCardDto transferStarbucksCardRequestDto);
}

