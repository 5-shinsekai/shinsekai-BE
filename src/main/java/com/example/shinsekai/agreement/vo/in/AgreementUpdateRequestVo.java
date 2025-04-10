package com.example.shinsekai.agreement.vo.in;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@ToString
@Getter
public class AgreementUpdateRequestVo {

    private Long agreementId;
    private String agreementTitle;
    private String agreementContent;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate storedExpiredDate;
}
