package com.example.shinsekai.member.vo.in;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class ChangePasswordVo {
    private String loginId;
    private String newPassword;
}
