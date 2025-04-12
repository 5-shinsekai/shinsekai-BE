package com.example.shinsekai.agreement.dto.in;

import com.example.shinsekai.agreement.entity.Agreement;
import com.example.shinsekai.agreement.entity.MemberAgreementList;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberAgreementListCreateRequestDto {

    private String memberUuid;
    private Agreement agreement;

    public MemberAgreementList toEntity() {
        return MemberAgreementList.builder()
                .memberUuid(memberUuid)
                .isAgree(true)
                .agreement(agreement)
                .build();
    }

    public static MemberAgreementListCreateRequestDto of(String memberUuid, Agreement agreement) {
        return MemberAgreementListCreateRequestDto.builder()
                .memberUuid(memberUuid)
                .agreement(agreement)
                .build();
    }
}
