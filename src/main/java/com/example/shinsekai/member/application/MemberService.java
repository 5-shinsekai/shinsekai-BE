package com.example.shinsekai.member.application;

import com.example.shinsekai.member.dto.in.FindIdRequestDto;
import com.example.shinsekai.member.dto.in.SignInRequestDto;
import com.example.shinsekai.member.dto.in.SignUpRequestDto;
import com.example.shinsekai.member.dto.out.FindIdResponseDto;
import com.example.shinsekai.member.dto.out.SignInResponseDto;
import org.springframework.security.core.userdetails.UserDetails;

public interface MemberService {

    void signUp(SignUpRequestDto signUpRequestDto);

    SignInResponseDto signIn(SignInRequestDto signInRequestDto);

    void logout(String accessToken);

    FindIdResponseDto findId(FindIdRequestDto findIdRequestDto);

    UserDetails loadUserByUsername(String memberUuid);

}

