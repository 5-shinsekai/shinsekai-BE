package com.example.shinsekai.common.email.vo;

import com.example.shinsekai.common.email.entity.EmailType;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class VerificationCodeVo {
    private String email;
    private String code;
    private EmailType mailType;
}
