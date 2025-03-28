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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String cardNumber;

    @Column(nullable = false)
    private String pinNumber;

    private double amount = 0;

    private boolean isRegistered = false;

    @Builder
    public StarbucksCardKeyMap(Long id, String cardNumber, String pinNumber, double amount, boolean isRegistered) {
        this.id = id;
        this.cardNumber = cardNumber;
        this.pinNumber = pinNumber;
        this.amount = amount;
        this.isRegistered = isRegistered;
    }

}
