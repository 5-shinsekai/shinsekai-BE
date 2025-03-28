package com.example.shinsekai.card.dto.in;

import com.example.shinsekai.card.entity.StarbucksCardKeyMap;
import com.example.shinsekai.card.vo.StarbucksCardKeyMapRequestVo;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class StarbucksCardKeyMapRequestDto {
    private Long id;
    private String cardNumber;
    private String pinNumber;
    private double amount;
    private boolean isRegistered;

    public StarbucksCardKeyMap toEntity(){
        return StarbucksCardKeyMap.builder()
                .cardNumber(cardNumber)
                .pinNumber(pinNumber)
                .amount(amount)
                .build();
    }

    public StarbucksCardKeyMap toEntityUpdate(){
        return StarbucksCardKeyMap.builder()
                .id(id)
                .cardNumber(cardNumber)
                .pinNumber(pinNumber)
                .amount(amount)
                .build();
    }

    public static StarbucksCardKeyMapRequestDto from(StarbucksCardKeyMapRequestVo starbucksCardKeyMapRequestVo){
        return StarbucksCardKeyMapRequestDto.builder()
                .cardNumber(starbucksCardKeyMapRequestVo.getCardNumber())
                .pinNumber(starbucksCardKeyMapRequestVo.getPinNumber())
                .amount(starbucksCardKeyMapRequestVo.getAmount())
                .isRegistered(starbucksCardKeyMapRequestVo.isRegistered())
                .build();
    }
}
