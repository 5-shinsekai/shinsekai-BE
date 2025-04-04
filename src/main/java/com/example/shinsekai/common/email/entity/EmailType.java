package com.example.shinsekai.common.email.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EmailType {
    FIND_LOGIN_ID("아이디찾기"),
    CHANGE_PASSWORD("비밀번호변경"),
    SIGN_UP("회원가입");

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
