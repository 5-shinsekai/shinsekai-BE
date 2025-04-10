package com.example.shinsekai.payment.entity;

import com.example.shinsekai.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false, updatable = false)
    private String paymentCode;

    @Column(updatable = false)
    private String paymentKey;

    @Column(nullable = false, updatable = false)
    private String memberUuid;

    @Column(nullable = false, updatable = false)
    private String paymentMethod;

    @Column(nullable = false, updatable = false)
    private double paymentPrice;

    private String purchaseName;
    private String status;
    private String receiptUrl;
    private LocalDateTime approvedAt;

    //Card 정보
    private String cardName;
    private String cardNumber;
    private String approveNo;
    private boolean isInterestFree;
    private int installmentPlanMonths;
    private boolean useCardPoint;

    //스타벅스 카드 uuid
    private String starbucksCardUuid;

}
