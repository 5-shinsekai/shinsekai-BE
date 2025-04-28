package com.example.shinsekai.agreement.dto.in;

import com.example.shinsekai.agreement.entity.Agreement;
import com.example.shinsekai.agreement.vo.in.AgreementUpdateRequestVo;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;


@Builder
@Getter
@ToString
public class AgreementUpdateRequestDto {

    private Long agreementId;
    private String agreementTitle;
    private String agreementContent;
    private LocalDate storedExpiredDate;

    public Agreement toEntity(Agreement savedAgreement) {
        return Agreement.builder()
                .id(agreementId)
                .agreementTitle
                        (this.agreementTitle == null || this.agreementTitle.trim().isBlank() ?
                                savedAgreement.getAgreementTitle() : this.agreementTitle)
                .agreementContent
                        (this.agreementContent == null || this.agreementContent.trim().isBlank() ?
                                savedAgreement.getAgreementContent() : this.agreementContent)
                .storedExpiredDate
                        (this.storedExpiredDate == null ?
                                savedAgreement.getStoredExpiredDate() : this.storedExpiredDate)
                .build();
    }


    public static AgreementUpdateRequestDto from(AgreementUpdateRequestVo agreementUpdateRequestVo) {
        return AgreementUpdateRequestDto.builder()
                .agreementId(agreementUpdateRequestVo.getAgreementId())
                .agreementTitle(agreementUpdateRequestVo.getAgreementTitle())
                .agreementContent(agreementUpdateRequestVo.getAgreementContent())
                .storedExpiredDate(agreementUpdateRequestVo.getStoredExpiredDate())
                .build();
    }
}
