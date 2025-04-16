package com.example.shinsekai.member.dto.in;

import com.example.shinsekai.member.vo.in.SocialMemberUpdateRequestVo;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SocialMemberUpdateRequestDto {

    private String loginId;
    private String password;
    private Long socialId;

    @Builder
    public SocialMemberUpdateRequestDto(String loginId, String password, Long socialId) {
        this.loginId = loginId;
        this.password = password;
        this.socialId = socialId;
    }


    public static SocialMemberUpdateRequestDto from(SocialMemberUpdateRequestVo socialMemberUpdateRequestVo) {
        return SocialMemberUpdateRequestDto.builder()
                .loginId(socialMemberUpdateRequestVo.getLoginId())
                .password(socialMemberUpdateRequestVo.getPassword())
                .socialId(socialMemberUpdateRequestVo.getSocialId())
                .build();
    }
}
