package com.example.shinsekai.common.email.dto.in;

import com.example.shinsekai.common.email.entity.EmailType;
import com.example.shinsekai.common.email.vo.EmailVerificationVo;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class EmailVerificationRequestDto {
    private String email;
    private EmailType mailType;

    public static EmailVerificationRequestDto from(EmailVerificationVo emailVerificationVo) {
        return EmailVerificationRequestDto.builder()
                .email(emailVerificationVo.getEmail())
                .mailType(emailVerificationVo.getMailType())
                .build();
    }


}
