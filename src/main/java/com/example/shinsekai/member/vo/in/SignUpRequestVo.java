package com.example.shinsekai.member.vo.in;

import com.example.shinsekai.member.entity.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class SignUpRequestVo {

    @NotBlank(message = "INVALID_LOGIN_ID_FORMAT")
    @Size(max = 30, message = "INVALID_LOGIN_ID_FORMAT")
    @Pattern(regexp = "^(?=.*[a-z])[a-z\\d]{1,30}$", message = "INVALID_LOGIN_ID_FORMAT")
    private String loginId;

    @NotBlank(message = "INVALID_EMAIL_FORMAT")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", message = "INVALID_EMAIL_FORMAT")
    private String email;

    @NotBlank(message = "INVALID_PASSWORD_FORMAT")
    @Size(min = 8, max = 20, message = "INVALID_PASSWORD_FORMAT")
    @Pattern(regexp = "^(?=\\S+$)(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[a-zA-Z\\d@$!%*?&]{8,20}$", message = "INVALID_PASSWORD_FORMAT")
    private String password;

    @NotBlank(message = "INVALID_NICKNAME_FORMAT")
    @Size(max = 30, message = "INVALID_NICKNAME_FORMAT")
    private String nickname;

    @NotBlank(message = "INVALID_PHONE_FORMAT")
    @Pattern(regexp = "^\\d{3}-\\d{4}-\\d{4}$", message = "INVALID_PHONE_FORMAT")
    private String phone;

    private Gender gender;

    @NotBlank(message = "INVALID_NAME_FORMAT")
    @Pattern(regexp = "^[a-zA-Z가-힣]+$", message = "INVALID_NAME_FORMAT")
    private String name;

    @Past(message = "INVALID_BIRTH_PAST")
    @NotNull(message = "INVALID_BIRTH_FORMAT")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birth;
}