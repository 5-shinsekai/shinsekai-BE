package com.example.shinsekai.card.vo;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class StarbucksCardRequestVo {
    private Long id;
    private String cardUuid;
    private String memberUuid;
    private String cardName;
    private String cardNumber;
    private String pinNumber;
    private String cardImageUrl;
    private String cardDescription;
}
