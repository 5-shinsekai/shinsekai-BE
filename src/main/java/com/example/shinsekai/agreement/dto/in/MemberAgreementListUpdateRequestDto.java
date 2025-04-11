package com.example.shinsekai.agreement.dto.in;

import com.example.shinsekai.agreement.entity.Agreement;
import com.example.shinsekai.agreement.entity.MemberAgreementList;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class MemberAgreementListUpdateRequestDto {

    private Long memberAgreementId;
    private String memberUuid;
    private Agreement agreement;
    private Boolean isAgree;
    private LocalDate expiredDate;

    public MemberAgreementList toEntity(MemberAgreementList savedMemberAgreementList) {
        return MemberAgreementList.builder()
                .id(memberAgreementId)
                .memberUuid(memberUuid)
                .agreement(agreement)
                .isAgree(isAgree == null ? savedMemberAgreementList.getIsAgree() : isAgree)
                .expiredDate(expiredDate == null ? savedMemberAgreementList.getExpiredDate() : expiredDate)
                .build();
    }
}
