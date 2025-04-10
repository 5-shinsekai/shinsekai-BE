package com.example.shinsekai.agreement.application;

import com.example.shinsekai.agreement.dto.in.AgreementCreateRequestDto;
import com.example.shinsekai.agreement.dto.in.AgreementUpdateRequestDto;
import com.example.shinsekai.agreement.dto.out.AgreementResponseDto;
import com.example.shinsekai.agreement.entity.Agreement;
import com.example.shinsekai.agreement.infrastructure.AgreementRepository;
import com.example.shinsekai.common.entity.BaseResponseStatus;
import com.example.shinsekai.common.exception.BaseException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class AgreementServiceImpl implements AgreementService{

    private final AgreementRepository agreementRepository;

    @Override
    public List<AgreementResponseDto> getAllAgreement() {
        return agreementRepository.findAll().stream()
                .map(AgreementResponseDto::from)
                .filter(agreement -> agreement.getStoredExpiredDate() == null)
                .collect(Collectors.toList());
    }

    @Override
    public AgreementResponseDto getAgreement(Long agreementId) {
        Agreement agreement = agreementRepository.findById(agreementId).orElseThrow(
                () -> new BaseException(BaseResponseStatus.NO_EXIST_AGREEMENT));
        return AgreementResponseDto.from(agreement);
    }

    @Override
    @Transactional
    public void createAgreement(AgreementCreateRequestDto agreementCreateRequestDto) {
        agreementRepository.save(agreementCreateRequestDto.toEntity());
    }

    @Override
    @Transactional
    public void updateAgreement(AgreementUpdateRequestDto agreementUpdateRequestDto) {
        Agreement agreement = agreementRepository.findById(agreementUpdateRequestDto.getAgreementId()).orElseThrow(
                () -> new BaseException(BaseResponseStatus.NO_EXIST_AGREEMENT));
        agreementRepository.save(agreementUpdateRequestDto.toEntity(agreement));
    }

    @Override
    @Transactional
    public void deleteAgreement(Long agreementId) {
        agreementRepository.deleteById(agreementId);
    }
}
