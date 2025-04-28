package com.example.shinsekai.card.dto.out;

import com.example.shinsekai.card.entity.StarbucksCard;
import com.example.shinsekai.card.vo.out.StarbucksCardResponseVo;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StarbucksCardResponseDto {
    private String memberStarbucksCardListUuid;
    private String cardNumber;
    private String cardName;
    private String cardImageUrl;
    private Double remainAmount;
    private Boolean agreed;

    public static StarbucksCardResponseDto from(StarbucksCard starbucksCard, String memberStarbucksCardListUuid) {
        return StarbucksCardResponseDto.builder()
                .memberStarbucksCardListUuid(memberStarbucksCardListUuid)
                .cardName(starbucksCard.getCardName())
                .cardNumber(starbucksCard.getCardNumber())
                .cardImageUrl(starbucksCard.getCardImageUrl())
                .remainAmount(starbucksCard.getRemainAmount())
                .agreed(starbucksCard.getAgreed())
                .build();
    }

    public StarbucksCardResponseVo toVo() {
        return StarbucksCardResponseVo.builder()
                .memberStarbucksCardListUuid(memberStarbucksCardListUuid)
                .cardName(cardName)
                .cardNumber(cardNumber.substring(cardNumber.length() - 6))
                .cardImageUrl(cardImageUrl)
                .remainAmount(remainAmount)
                .build();
    }
}
