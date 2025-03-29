package com.example.shinsekai.card.application;

import com.example.shinsekai.card.dto.in.StarbucksCardRequestDto;
import com.example.shinsekai.card.dto.out.StarbucksCardResponseDto;

import java.util.List;

public interface StarbucksCardService {
    public List<StarbucksCardResponseDto> getStarbucksCard(String memberUuid);
    public void createStarbucksCard(StarbucksCardRequestDto starbucksCardDto);
}
