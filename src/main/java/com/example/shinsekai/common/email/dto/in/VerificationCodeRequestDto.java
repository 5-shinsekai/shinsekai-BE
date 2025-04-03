package com.example.shinsekai.common.email.dto.in;

import com.example.shinsekai.common.email.vo.VerificationCodeVo;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class VerificationCodeRequestDto {
    private String email;
    private String code;

    public static VerificationCodeRequestDto from(VerificationCodeVo verificationCodeVo) {
        return VerificationCodeRequestDto.builder()
                .email(verificationCodeVo.getEmail())
                .code(verificationCodeVo.getCode())
                .build();
    }
}
