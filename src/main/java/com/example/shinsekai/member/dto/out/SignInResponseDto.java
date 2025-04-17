package com.example.shinsekai.member.dto.out;

import com.example.shinsekai.member.entity.Member;
import com.example.shinsekai.member.vo.out.SignInResponseVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class SignInResponseDto {

    private String accessToken;
    private String refreshToken;
    private String name;

    @Builder
    public SignInResponseDto(String accessToken, String refreshToken, String name) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.name = name;
    }

    public static SignInResponseDto of(Member member, String accessToken, String refreshToken) {
        return SignInResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .name(member.getName())
                .build();
    }

    public SignInResponseVo toVo() {
        return SignInResponseVo.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .name(name)
                .build();
    }


}
