package com.example.shinsekai.member.dto.in;

import com.example.shinsekai.member.entity.Member;
import com.example.shinsekai.member.vo.in.ChangePasswordVo;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Builder
@ToString
public class ChangePasswordRequestDto {
    private String accessToken;
    private String loginId;
    private String newPassword;

    @Builder
    public static ChangePasswordRequestDto from(ChangePasswordVo changePasswordVo, String accessToken) {
        return ChangePasswordRequestDto.builder()
                .accessToken(accessToken)
                .loginId(changePasswordVo.getLoginId())
                .newPassword(changePasswordVo.getNewPassword())
                .build();
    }
}
