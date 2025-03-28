package com.example.shinsekai.card.vo;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class StarbucksCardKeyMapRequestVo {
    private String cardNumber;
    private String pinNumber;
    private double amount;
    private boolean isRegistered;
}
