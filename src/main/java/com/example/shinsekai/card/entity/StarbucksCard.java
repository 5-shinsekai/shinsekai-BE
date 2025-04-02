package com.example.shinsekai.card.entity;

import com.example.shinsekai.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Entity
@Getter
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
    private double remainAmount;

    @Builder
    public StarbucksCard(
            Long id,
            String cardUuid,
            String cardName,
            String cardNumber,
            String cardImageUrl,
            String cardDescription,
            double remainAmount
    ) {

        this.id = id;
        this.cardUuid = cardUuid;
        this.cardName = cardName;
        this.cardNumber = cardNumber;
        this.cardImageUrl = cardImageUrl;
        this.cardDescription = cardDescription;
        this.remainAmount = remainAmount;
    }

}
