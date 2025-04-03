package com.example.shinsekai.common.email.presentation;

import com.example.shinsekai.common.email.application.EmailService;
import com.example.shinsekai.common.email.dto.in.EmailAuthRequestDto;
import com.example.shinsekai.common.email.vo.EmailAuthVo;
import com.example.shinsekai.common.entity.BaseResponseEntity;
import com.example.shinsekai.common.entity.BaseResponseStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/emailAuth")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/request")
    public BaseResponseEntity<Boolean> sendVerificationCode(@RequestBody EmailAuthVo emailAuthVo) {
        emailService.sendVerificationEmail(EmailAuthRequestDto.from(emailAuthVo));
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }


}
