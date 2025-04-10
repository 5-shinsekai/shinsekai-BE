package com.example.shinsekai.card.application;

import com.example.shinsekai.card.dto.in.StarbucksCardRequestDto;
import com.example.shinsekai.card.dto.out.StarbucksCardResponseDto;
import com.example.shinsekai.card.entity.StarbucksCard;

import java.util.List;

public interface StarbucksCardService {
    List<StarbucksCardResponseDto> getActiveStarbucksCards(String memberUuid);
    void createStarbucksCard(StarbucksCardRequestDto starbucksCardRequestDto);
    void deleteStarbucksCard(String StarbucksCardUuid, String memberUuid);
}

