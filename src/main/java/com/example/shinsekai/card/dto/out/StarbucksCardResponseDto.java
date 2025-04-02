package com.example.shinsekai.card.dto.out;

import com.example.shinsekai.card.entity.StarbucksCard;
import com.example.shinsekai.card.vo.out.StarbucksCardResponseVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class StarbucksCardResponseDto {
    private String memberStarbucksCardListUuid;
    private String cardNumber;
    private String cardName;
    private String cardImageUrl;
    private double remainAmount;

    @Builder
    public StarbucksCardResponseDto(String memberStarbucksCardListUuid,String cardNumber, String cardName, String cardImageUrl, double remainAmount) {
        this.memberStarbucksCardListUuid = memberStarbucksCardListUuid;
        this.cardNumber = cardNumber;
        this.cardName = cardName;
        this.cardImageUrl = cardImageUrl;
        this.remainAmount = remainAmount;
    }

    public static StarbucksCardResponseDto from(StarbucksCard starbucksCard, String memberStarbucksCardListUuid){
        return StarbucksCardResponseDto.builder()
                .memberStarbucksCardListUuid(memberStarbucksCardListUuid)
                .cardName(starbucksCard.getCardName())
                .cardNumber(starbucksCard.getCardNumber())
                .cardImageUrl(starbucksCard.getCardImageUrl())
                .remainAmount(starbucksCard.getRemainAmount())
                .build();
    }

    public StarbucksCardResponseVo toVo(){
        return StarbucksCardResponseVo.builder()
                .memberStarbucksCardListUuid(memberStarbucksCardListUuid)
                .cardName(cardName)
                .cardNumber(cardNumber.substring(cardNumber.length() - 6))
                .cardImageUrl(cardImageUrl)
                .remainAmount(remainAmount)
                .build();
    }
}
