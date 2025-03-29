package com.example.shinsekai.card.dto.in;

import com.example.shinsekai.card.entity.StarbucksCard;
import com.example.shinsekai.card.vo.StarbucksCardRequestVo;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class StarbucksCardRequestDto {
    private Long id;
    private String cardUuid;
    private String memberUuid;
    private String cardName;
    private String cardNumber;
    private String pinNumber;
    private String cardImageUrl;
    private String cardDescription;
    private double remainAmount;

    public StarbucksCard toEntity(String memberUuid, String cardUuid, double remainAmount){
        return StarbucksCard.builder()
                .memberUuid(memberUuid)
                .cardUuid(cardUuid)
                .cardName(cardName)
                .cardNumber(cardNumber)
                .cardImageUrl(cardImageUrl)
                .cardDescription(cardDescription)
                .remainAmount(remainAmount)
                .build();
    }

    public static StarbucksCardRequestDto from(StarbucksCardRequestVo starbucksCardRequestVo){
        return StarbucksCardRequestDto.builder()
                .cardName(starbucksCardRequestVo.getCardName())
                .cardNumber(starbucksCardRequestVo.getCardNumber())
                .cardImageUrl(starbucksCardRequestVo.getCardImageUrl())
                .memberUuid(starbucksCardRequestVo.getMemberUuid())
                .pinNumber(starbucksCardRequestVo.getPinNumber())
                .cardDescription(starbucksCardRequestVo.getCardDescription())
                .build();
    }
}
