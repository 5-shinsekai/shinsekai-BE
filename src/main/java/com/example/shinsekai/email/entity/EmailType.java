package com.example.shinsekai.email.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EmailType {
    FIND_LOGIN_ID("아이디찾기"),
    SIGN_UP("회원가입"),
    TEMP_PW("임시비밀번호");

    private final String mailType;

    @JsonValue
    public String getMailType() {
        return mailType;
    }

    @JsonCreator
    public static EmailType fromString(String value) {
        for (EmailType mailType : EmailType.values()) {
            if (mailType.mailType.equals(value)) {
                return mailType;
            }
        }
        throw new IllegalArgumentException("UnKnown value: " + value);
    }

}
