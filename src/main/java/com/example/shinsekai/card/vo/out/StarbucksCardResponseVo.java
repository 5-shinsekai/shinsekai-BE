package com.example.shinsekai.card.vo.out;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class StarbucksCardResponseVo {
    private String memberStarbucksCardListUuid;
    private String cardName;
    private String cardNumber;
    private String cardImageUrl;
    private Double remainAmount;

    @Builder
    public StarbucksCardResponseVo(String memberStarbucksCardListUuid,
                                   String cardName,
                                   String cardNumber,
                                   String cardImageUrl,
                                   Double remainAmount) {
        this.memberStarbucksCardListUuid = memberStarbucksCardListUuid;
        this.cardName = cardName;
        this.cardNumber = cardNumber;
        this.cardImageUrl = cardImageUrl;
        this.remainAmount = remainAmount;
    }
}
