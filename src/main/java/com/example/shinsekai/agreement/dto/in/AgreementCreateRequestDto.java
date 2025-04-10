package com.example.shinsekai.agreement.dto.in;

import com.example.shinsekai.agreement.entity.Agreement;
import com.example.shinsekai.agreement.vo.in.AgreementCreateRequestVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@ToString
public class AgreementCreateRequestDto {

    private String agreementTitle;
    private String agreementContent;

    public Agreement toEntity() {
        return Agreement.builder()
                .agreementTitle(agreementTitle)
                .agreementContent(agreementContent)
                .build();
    }

    public static AgreementCreateRequestDto from(AgreementCreateRequestVo agreementCreateRequestVo) {
        return AgreementCreateRequestDto.builder()
                .agreementTitle(agreementCreateRequestVo.getAgreementTitle())
                .agreementContent(agreementCreateRequestVo.getAgreementContent())
                .build();
    }
}
