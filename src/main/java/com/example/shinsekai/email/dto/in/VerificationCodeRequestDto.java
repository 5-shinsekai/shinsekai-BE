package com.example.shinsekai.email.dto.in;

import com.example.shinsekai.email.entity.EmailType;
import com.example.shinsekai.email.vo.VerificationCodeVo;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class VerificationCodeRequestDto {
    private String email;
    private String code;
    private EmailType mailType;

    public static VerificationCodeRequestDto from(VerificationCodeVo verificationCodeVo) {
        return VerificationCodeRequestDto.builder()
                .email(verificationCodeVo.getEmail())
                .code(verificationCodeVo.getCode())
                .mailType(verificationCodeVo.getMailType())
                .build();
    }
}
