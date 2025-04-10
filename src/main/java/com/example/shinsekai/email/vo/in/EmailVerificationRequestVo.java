package com.example.shinsekai.email.vo.in;

import com.example.shinsekai.email.entity.EmailType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class EmailVerificationRequestVo {

    @NotBlank(message = "INVALID_EMAIL_FORMAT")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", message = "INVALID_EMAIL_FORMAT")
    private String email;
    private EmailType mailType;
}
