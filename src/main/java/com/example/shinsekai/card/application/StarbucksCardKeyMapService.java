package com.example.shinsekai.card.application;

import com.example.shinsekai.card.dto.in.StarbucksCardKeyMapRequestDto;
import com.example.shinsekai.card.entity.StarbucksCardKeyMap;

import java.util.Optional;

public interface StarbucksCardKeyMapService {
    public boolean matchStarbucksCardAndPin(StarbucksCardKeyMap starbucksCardKeyMap,String pinNumber);
    public Optional<StarbucksCardKeyMap> getStarbucksCardKeyMap(String cardNumber);
    public void updateStarbuckscardKeyMap(StarbucksCardKeyMapRequestDto starbucksCardKeyMapRequestDto);
    
    // 임시 등록
    public void createStarbucksCardKeyMap(StarbucksCardKeyMapRequestDto starbucksCardKeyMapRequestDto);
}
