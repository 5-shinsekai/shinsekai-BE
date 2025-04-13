package com.example.shinsekai.agreement.dto.out;

import com.example.shinsekai.agreement.entity.Agreement;
import com.example.shinsekai.agreement.vo.out.AgreementResponseVo;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class AgreementResponseDto {

    private Long agreementId;
    private String agreementTitle;
    private String agreementContent;
    private LocalDate storedExpiredDate;

    @Builder
    public AgreementResponseDto(Long agreementId,
                                String agreementTitle,
                                String agreementContent,
                                LocalDate storedExpiredDate) {
        this.agreementId = agreementId;
        this.agreementTitle = agreementTitle;
        this.agreementContent = agreementContent;
        this.storedExpiredDate = storedExpiredDate;
    }

    public static AgreementResponseDto from(Agreement agreement) {
        return AgreementResponseDto.builder()
                .agreementId(agreement.getId())
                .agreementTitle(agreement.getAgreementTitle())
                .agreementContent(agreement.getAgreementContent())
                .build();
    }

    public static AgreementResponseVo toVo(AgreementResponseDto agreementResponseDto) {
        return AgreementResponseVo.builder()
                .agreementId(agreementResponseDto.getAgreementId())
                .agreementTitle(agreementResponseDto.getAgreementTitle())
                .agreementContent(agreementResponseDto.getAgreementContent())
                .storedExpiredDate(agreementResponseDto.getStoredExpiredDate())
                .build();
    }
}
