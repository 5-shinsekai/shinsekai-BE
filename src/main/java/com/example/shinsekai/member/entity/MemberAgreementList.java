package com.example.shinsekai.member.entity;

import com.example.shinsekai.agreement.entity.Agreement;
import com.example.shinsekai.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberAgreementList extends BaseEntity {

    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Agreement agreement;

    @Column(nullable = false)
    private String memberUuid;

    @Column(nullable = false)
    private boolean isAgree;

    @Builder
    public MemberAgreementList(Long id
            , Agreement agreement
            , String memberUuid
            , boolean isAgree) {
        this.id = id;
        this.agreement = agreement;
        this.memberUuid = memberUuid;
        this.isAgree = isAgree;
    }


}
