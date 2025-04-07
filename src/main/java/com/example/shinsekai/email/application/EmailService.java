package com.example.shinsekai.email.application;

import com.example.shinsekai.email.dto.in.EmailVerificationRequestDto;
import com.example.shinsekai.email.dto.in.VerificationCodeRequestDto;

public interface EmailService {

    void sendVerificationEmail(EmailVerificationRequestDto emailVerificationRequestDto);

    void verifyCode(VerificationCodeRequestDto verificationCodeRequestDto);
}
