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
    private String loginId;
    private String newPassword;

    public Member toEntity(PasswordEncoder passwordEncoder) {
        return Member.builder()
                .loginId(loginId)
                .password(passwordEncoder.encode(newPassword))
                .build();
    }

    @Builder
    public static ChangePasswordRequestDto from(ChangePasswordVo changePasswordVo) {
        return ChangePasswordRequestDto.builder()
                .loginId(changePasswordVo.getLoginId())
                .newPassword(changePasswordVo.getNewPassword())
                .build();
    }
}
