package com.example.shinsekai.member.application;

import com.example.shinsekai.member.dto.in.ChangePasswordRequestDto;
import com.example.shinsekai.member.dto.in.SignInRequestDto;
import com.example.shinsekai.member.dto.in.SignUpRequestDto;
import com.example.shinsekai.member.dto.out.SignInResponseDto;

public interface MemberService {

    void signUp(SignUpRequestDto signUpRequestDto);
    SignInResponseDto signIn(SignInRequestDto signInRequestDto);
    void logout();
    boolean checkId(String loginId);
    String findId(String email, String code);
    void changePassword(ChangePasswordRequestDto changePasswordRequestDto);
}

