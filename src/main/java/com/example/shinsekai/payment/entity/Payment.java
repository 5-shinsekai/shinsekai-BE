package com.example.shinsekai.payment.entity;

import com.example.shinsekai.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

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

    @Column(nullable = false, updatable = false)
    private String memberUuid;

    @Column(nullable = false, updatable = false)
    private String paymentMethod;

    @Column(nullable = false, updatable = false)
    private double paymentPrice;

    private String purchaseName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;

    private String receiptUrl;

    private String starbucksCardUuid;


    public void cancelPayment() {
        this.status = PaymentStatus.CANCEL;
    }
}
