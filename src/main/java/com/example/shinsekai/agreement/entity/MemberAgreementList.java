package com.example.shinsekai.agreement.entity;

import com.example.shinsekai.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberAgreementList extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String memberUuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agreement_id")
    private Agreement agreement;

    @Column(columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean isAgree;

    private LocalDate expiredDate;

    @Builder
    public MemberAgreementList(Long id,
                               String memberUuid,
                               Agreement agreement,
                               Boolean isAgree,
                               LocalDate expiredDate) {
        this.id = id;
        this.memberUuid = memberUuid;
        this.agreement = agreement;
        this.isAgree = isAgree;
        this.expiredDate = expiredDate;
    }
}
