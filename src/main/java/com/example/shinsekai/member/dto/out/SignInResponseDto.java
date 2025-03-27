package com.example.shinsekai.member.dto.out;

import com.example.shinsekai.member.entity.Member;
import com.example.shinsekai.member.vo.SignInResponseVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class SignInResponseDto {

    private String accessToken;
    private String memberUuid;
    private String name;

    @Builder
    public SignInResponseDto(String accessToken, String memberUuid, String name) {
        this.accessToken = accessToken;
        this.memberUuid = memberUuid;
        this.name = name;
    }

    public static SignInResponseDto from(Member member, String accessToken) {
        return SignInResponseDto.builder()
                .accessToken(accessToken)
                .memberUuid(member.getMemberUuid())
                .name(member.getName())
                .build();
    }

    public SignInResponseVo toVo() {
        return SignInResponseVo.builder()
                .accessToken(accessToken)
                .memberUuid(memberUuid)
                .name(name)
                .build();
    }


}
