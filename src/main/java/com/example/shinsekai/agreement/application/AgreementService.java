package com.example.shinsekai.agreement.application;

import com.example.shinsekai.agreement.dto.in.AgreementCreateRequestDto;
import com.example.shinsekai.agreement.dto.in.AgreementUpdateRequestDto;
import com.example.shinsekai.agreement.dto.out.AgreementResponseDto;

import java.util.List;

public interface AgreementService {

    List<AgreementResponseDto> getAllAgreement();
    AgreementResponseDto getAgreement(Long agreementId);
    void createAgreement(AgreementCreateRequestDto agreementCreateRequestDto);
    void updateAgreement(AgreementUpdateRequestDto agreementUpdateRequestDto);
    void deleteAgreement(Long agreementId);
}
