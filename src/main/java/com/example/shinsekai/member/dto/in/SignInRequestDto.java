package com.example.shinsekai.member.dto.in;

import com.example.shinsekai.member.vo.in.SignInRequestVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class SignInRequestDto {
    private String loginId;
    private String password;

    @Builder
    public SignInRequestDto(String loginId, String password) {
        this.loginId = loginId;
        this.password = password;
    }

    public static SignInRequestDto from(SignInRequestVo signInRequestVo) {
        return SignInRequestDto.builder()
                .loginId(signInRequestVo.getLoginId())
                .password(signInRequestVo.getPassword())
                .build();
    }
}
