package com.example.shinsekai.common.email.vo;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class VerificationCodeVo {
    private String email;
    private String code;
}
