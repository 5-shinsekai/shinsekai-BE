package com.example.shinsekai.card.vo.in;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class StarbucksCardRequestVo {
    private String cardName;
    private String cardNumber;
    private String cardImageUrl;
    private String cardDescription;
    private Double remainAmount;
    private Boolean agreed;
}
