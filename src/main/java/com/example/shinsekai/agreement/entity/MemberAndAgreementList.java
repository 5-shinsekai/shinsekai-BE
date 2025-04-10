package com.example.shinsekai.agreement.entity;

import com.example.shinsekai.common.entity.BaseEntity;
import jakarta.persistence.*;

import java.time.LocalDate;

public class MemberAndAgreementList extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String memberUuid;

    @Column(nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Long agreementId;

    @Column(columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean isAgree;

    private LocalDate ExpiredDate;
}
