package com.example.shinsekai.email.dto.in;

import com.example.shinsekai.email.entity.EmailType;
import com.example.shinsekai.email.vo.in.EmailVerificationRequestVo;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class EmailVerificationRequestDto {
    private String email;
    private EmailType mailType;

    public static EmailVerificationRequestDto from(EmailVerificationRequestVo emailVerificationRequestVo) {
        return EmailVerificationRequestDto.builder()
                .email(emailVerificationRequestVo.getEmail())
                .mailType(emailVerificationRequestVo.getMailType())
                .build();
    }


}
