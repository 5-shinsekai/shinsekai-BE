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
    private Long id; // uuid 쓰기 id 로 통일

    @Column(unique = true, nullable = false, updatable = false)
    private String cardUuid;

    @Column(updatable = false, nullable = false, length = 50)
    private String memberUuid; // 중개테이블

    @Column(nullable = false, length = 50)
    private String cardName;

    @Column(nullable = false, updatable = false, unique = false, length = 16)
    private String cardNumber;

    @Column(nullable = false, length = 100)
    private String cardImageUrl;//섬네일 이미지
    private String cardDescription;
    private double remainAmount;

    @Builder
    public StarbucksCard(
            Long id,
            String cardUuid,
            String memberUuid,
            String cardName,
            String cardNumber,
            String cardImageUrl,
            String cardDescription,
            double remainAmount) {

        this.id = id;
        this.cardUuid = cardUuid;
        this.memberUuid = memberUuid;
        this.cardName = cardName;
        this.cardNumber = cardNumber;
        this.cardImageUrl = cardImageUrl;
        this.cardDescription = cardDescription;
        this.remainAmount = remainAmount;
    }

}
