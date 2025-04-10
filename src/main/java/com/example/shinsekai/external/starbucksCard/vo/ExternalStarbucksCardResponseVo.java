package com.example.shinsekai.external.starbucksCard.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class ExternalStarbucksCardResponseVo {
    private String cardNumber;
    private Double remainAmount;
    private String cardImageUrl;
    private String cardDescription;
}
