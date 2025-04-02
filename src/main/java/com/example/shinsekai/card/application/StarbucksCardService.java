package com.example.shinsekai.card.application;

import com.example.shinsekai.card.dto.in.StarbucksCardRequestDto;
import com.example.shinsekai.card.dto.out.StarbucksCardResponseDto;
import com.example.shinsekai.card.entity.StarbucksCard;

import java.util.List;

public interface StarbucksCardService {
    List<StarbucksCardResponseDto> getStarbucksCard(String memberUuid); //uuid
    void createStarbucksCard(String memberUuid, StarbucksCardRequestDto starbucksCardRequestDto);
    void saveStarbucksCard(StarbucksCard starbucksCard);
    void saveMemberStarbucksCardList(String memberUuid, StarbucksCard starbucksCard);
    void deleteStarbucksCard(String StarbucksCardUuid);
}

