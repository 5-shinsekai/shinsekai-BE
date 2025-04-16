package com.example.shinsekai.member.vo.in;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class SocialMemberUpdateRequestVo {

    private String loginId;
    private String password;
    private Long socialId;
}
