package com.example.shinsekai.card.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class StarbucksCardResponseVo {
    private String cardName;
    private String cardNumber;
    private String cardImageUrl;
    private double remainAmount;

    @Builder
    public StarbucksCardResponseVo(String cardName, String cardNumber, String cardImageUrl, double remainAmount) {
        this.cardName = cardName;
        this.cardNumber = cardNumber;
        this.cardImageUrl = cardImageUrl;
        this.remainAmount = remainAmount;
    }
}
