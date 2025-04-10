package com.example.shinsekai.email.presentation;

import com.example.shinsekai.email.application.EmailService;
import com.example.shinsekai.email.dto.in.EmailVerificationRequestDto;
import com.example.shinsekai.email.dto.in.SendTempRequestDto;
import com.example.shinsekai.email.dto.in.VerificationCodeRequestDto;
import com.example.shinsekai.email.vo.in.EmailVerificationRequestVo;
import com.example.shinsekai.email.vo.in.VerificationCodeVo;
import com.example.shinsekai.common.entity.BaseResponseEntity;
import com.example.shinsekai.common.entity.BaseResponseStatus;
import com.example.shinsekai.member.vo.in.SendTempPwRequestVo;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/email")
@RequiredArgsConstructor
public class EmailController {
    private final EmailService emailService;

    @Operation(summary = "인증 메일 발송")
    @PostMapping("/send-verification")
    public BaseResponseEntity<Void> sendVerificationCodeForId(@RequestBody EmailVerificationRequestVo emailRequestVo) {
        emailService.sendVerificationEmail(EmailVerificationRequestDto.from(emailRequestVo));
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }

    @Operation(summary = "인증 메일 검증")
    @PostMapping("/submit-code")
    public BaseResponseEntity<Void> verifyCode(@Valid @RequestBody VerificationCodeVo verificationCodeVo) {
        emailService.verifyCode(VerificationCodeRequestDto.from(verificationCodeVo));
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }

    @Operation(summary = "임시 비밀번호 발급")
    @PostMapping("/temp-pw")
    public BaseResponseEntity<Void> sendTempPassword(@RequestBody SendTempPwRequestVo sendTempPwRequestVo) {
        emailService.sendTempPassword(SendTempRequestDto.from(sendTempPwRequestVo));
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }
}
