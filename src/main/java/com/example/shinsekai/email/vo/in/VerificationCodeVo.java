package com.example.shinsekai.email.vo.in;

import com.example.shinsekai.email.entity.EmailType;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class VerificationCodeVo {
    private String email;
    private String code;
    private EmailType mailType;
}
