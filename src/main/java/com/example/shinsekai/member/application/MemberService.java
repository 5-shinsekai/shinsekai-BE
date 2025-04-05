package com.example.shinsekai.member.application;

import com.example.shinsekai.member.dto.in.ChangePasswordRequestDto;
import com.example.shinsekai.member.dto.in.SignInRequestDto;
import com.example.shinsekai.member.dto.in.SignUpRequestDto;
import com.example.shinsekai.member.dto.out.FindIdResponseDto;
import com.example.shinsekai.member.dto.out.SignInResponseDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface MemberService {

    void signUp(SignUpRequestDto signUpRequestDto);

    SignInResponseDto signIn(SignInRequestDto signInRequestDto);

    void logout(String accessToken);

    FindIdResponseDto findId(String email, boolean emailVerified);

    void changePassword(ChangePasswordRequestDto changePasswordRequestDto);
}

