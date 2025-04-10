package com.example.shinsekai.email.dto.in;

import com.example.shinsekai.member.vo.in.SendTempPwRequestVo;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class SendTempRequestDto {
    private String loginId;
    private String email;

    public static SendTempRequestDto from(SendTempPwRequestVo sendTempPwRequestVo) {
        return SendTempRequestDto.builder()
                .loginId(sendTempPwRequestVo.getLoginId())
                .email(sendTempPwRequestVo.getEmail())
                .build();
    }
}
