package com.example.shinsekai.card.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class StarbucksCardKeyMap {
    @Id
    private String cardNumber;
    private String pinNumber;
    private int amount;

    @Builder
    public StarbucksCardKeyMap(String cardNumber, String pinNumber, int amount) {
        this.cardNumber = cardNumber;
        this.pinNumber = pinNumber;
        this.amount = amount;
    }

    //변경
    public boolean matchStarbucksCardAndPin(String pinNumber) {
        if(this.pinNumber.equals(pinNumber)) {
            return true;
        }
        return false;
    }
}
