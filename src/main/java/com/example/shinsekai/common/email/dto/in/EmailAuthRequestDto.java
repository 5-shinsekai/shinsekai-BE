package com.example.shinsekai.common.email.dto.in;

import com.example.shinsekai.common.email.vo.EmailAuthVo;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class EmailAuthRequestDto {

    private String email;
    private String subject;
    private String body;

    public static EmailAuthRequestDto from(EmailAuthVo emailAuthVo) {

        return EmailAuthRequestDto.builder()
                .email(emailAuthVo.getEmail())
                .build();
    }


}
