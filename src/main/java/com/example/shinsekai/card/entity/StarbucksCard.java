package com.example.shinsekai.card.entity;

import com.example.shinsekai.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StarbucksCard extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, updatable = false)
    private String cardUuid;

    @Column(nullable = false, length = 50)
    private String cardName;

    @Column(nullable = false, updatable = false, unique = false, length = 16)
    private String cardNumber;

    @Column(nullable = false, length = 100)
    private String cardImageUrl;
    private String cardDescription;
    private Double remainAmount;
    private Boolean agreed;

    public void useRemainAmount(Double price) {
        this.remainAmount -= price;
    }
    public void chargeRemainAmount(Double price) {this.remainAmount += price;}
}
