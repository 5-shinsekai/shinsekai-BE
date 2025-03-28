package com.example.shinsekai.card.dto.out;

import com.example.shinsekai.card.entity.StarbucksCard;
import com.example.shinsekai.card.vo.StarbucksCardResponseVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class StarbucksCardResponseDto {
    //뒤 6자리만 보낸다
    private String cardNumber;
    private String cardName;
    private String cardImageUrl;
    private double remainAmount;

    @Builder
    public StarbucksCardResponseDto(String cardNumber, String cardName, String cardImageUrl, double remainAmount) {
        this.cardNumber = cardNumber;
        this.cardName = cardName;
        this.cardImageUrl = cardImageUrl;
        this.remainAmount = remainAmount;
    }

    public static StarbucksCardResponseDto from(StarbucksCard starbucksCard){
        return StarbucksCardResponseDto.builder()
                .cardName(starbucksCard.getCardName())
                .cardNumber(starbucksCard.getCardNumber())
                .cardImageUrl(starbucksCard.getCardImageUrl())
                .remainAmount(starbucksCard.getRemainAmount())
                .build();
    }

    public StarbucksCardResponseVo toVo(){
        return StarbucksCardResponseVo.builder()
                .cardName(cardName)
                .cardNumber(cutStarbucksCardNumberToSix(cardNumber))
                .cardImageUrl(cardImageUrl)
                .remainAmount(remainAmount)
                .build();
    }

    private String cutStarbucksCardNumberToSix(String cardNumber){
        return cardNumber.substring(cardNumber.length() - 6);
    }
}
