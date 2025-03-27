package com.example.shinsekai.payment.entity;

import com.example.shinsekai.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false, updatable = false)
    private String payCode;

    @Column(nullable = false, updatable = false)
    private String memberUuid;

    @Column(nullable = false, updatable = false)
    private String payMethod;

    @Column(nullable = false, updatable = false)
    private double payPrice;

    @Column(nullable = false, updatable = false)
    private LocalDateTime payTimestamp;


    @Builder
    public Payment(Long id, String payCode, String memberUuid, String payMethod, double payPrice, LocalDateTime payTimestamp) {
        this.id = id;
        this.payCode = payCode;
        this.memberUuid = memberUuid;
        this.payMethod = payMethod;
        this.payPrice = payPrice;
        this.payTimestamp = payTimestamp;
    }
}
