package com.example.shinsekai.card.dto.in;

import com.example.shinsekai.card.entity.StarbucksCard;
import com.example.shinsekai.card.vo.in.StarbucksCardRequestVo;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class StarbucksCardRequestDto {
    private Long id;
    private String memberUuid;
    private String cardUuid;
    private String cardName;
    private String cardNumber;

    public StarbucksCard toEntity(String cardUuid, double remainAmount, String cardImageUrl, String cardDescription) {
        return StarbucksCard.builder()
                .cardUuid(cardUuid)
                .cardName(cardName)
                .cardNumber(cardNumber)
                .remainAmount(remainAmount)
                .cardImageUrl(cardImageUrl)
                .cardDescription(cardDescription)
                .build();
    }

    public static StarbucksCardRequestDto from(StarbucksCardRequestVo starbucksCardRequestVo){
        return StarbucksCardRequestDto.builder()
                .cardName(starbucksCardRequestVo.getCardName())
                .cardNumber(starbucksCardRequestVo.getCardNumber())
                .build();
    }
}
