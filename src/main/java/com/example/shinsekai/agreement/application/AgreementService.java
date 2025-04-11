package com.example.shinsekai.agreement.application;

import com.example.shinsekai.agreement.dto.in.AgreementCreateRequestDto;
import com.example.shinsekai.agreement.dto.in.AgreementUpdateRequestDto;
import com.example.shinsekai.agreement.dto.in.MemberAgreementListCreateRequestDto;
import com.example.shinsekai.agreement.dto.in.MemberAgreementListUpdateRequestDto;
import com.example.shinsekai.agreement.dto.out.AgreementResponseDto;

import java.util.List;

public interface AgreementService {

    List<AgreementResponseDto> getAllAgreement();
    AgreementResponseDto getAgreement(Long agreementId);
    void createAgreement(AgreementCreateRequestDto agreementCreateRequestDto);
    void updateAgreement(AgreementUpdateRequestDto agreementUpdateRequestDto);
    void deleteAgreement(Long agreementId);

    List<AgreementResponseDto> getAgreementByMemberUuid(String memberUuid);
    AgreementResponseDto getAgreementByMemberUuidAndAgreementId(String memberUuid, Long agreementId);
    void createMemberAgreementList(MemberAgreementListCreateRequestDto memberAgreementListRequestDto);
    void updateMemberAgreementList(MemberAgreementListUpdateRequestDto memberAgreementListUpdateRequestDto);
}
