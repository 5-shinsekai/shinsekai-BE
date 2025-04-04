package com.example.shinsekai.common.email.application;

import com.example.shinsekai.common.email.EmailEnum;
import com.example.shinsekai.common.email.dto.in.EmailVerificationRequestDto;
import com.example.shinsekai.common.email.dto.in.VerificationCodeRequestDto;

public interface EmailService {

    void sendVerificationEmail(EmailVerificationRequestDto emailVerificationRequestDto, EmailEnum mailType);

    void verifyCode(VerificationCodeRequestDto verificationCodeRequestDto);
}
