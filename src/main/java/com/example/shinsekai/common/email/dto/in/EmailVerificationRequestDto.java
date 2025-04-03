package com.example.shinsekai.common.email.dto.in;

import com.example.shinsekai.common.email.vo.EmailVerificationVo;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class EmailVerificationRequestDto {
    private String email;
    private String subject;
    private String body;

    public static EmailVerificationRequestDto from(EmailVerificationVo emailAuthVo) {

        return EmailVerificationRequestDto.builder()
                .email(emailAuthVo.getEmail())
                .build();
    }


}
