package com.example.shinsekai.agreement.vo.out;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@ToString
public class AgreementResponseVo {

    private Long agreementId;
    private String agreementTitle;
    private String agreementContent;
    private LocalDate storedExpiredDate;

    @Builder
    public AgreementResponseVo(
            Long agreementId,
            String agreementTitle,
            String agreementContent,
            LocalDate storedExpiredDate) {
        this.agreementId = agreementId;
        this.agreementTitle = agreementTitle;
        this.agreementContent = agreementContent;
        this.storedExpiredDate = storedExpiredDate;
    }
}
