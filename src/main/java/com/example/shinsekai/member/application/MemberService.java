package com.example.shinsekai.member.application;

import com.example.shinsekai.member.dto.in.SignInRequestDto;
import com.example.shinsekai.member.dto.in.SignUpRequestDto;
import com.example.shinsekai.member.dto.out.SignInResponseDto;
import org.springframework.security.core.userdetails.UserDetails;

public interface MemberService {

    /**
     * @param signUpRequestDto
     */
    void signUp(SignUpRequestDto signUpRequestDto);

    /**
     * @param signInRequestDto
     * @return SignInResponseDto
     */
    SignInResponseDto signIn(SignInRequestDto signInRequestDto);

    /**
     * @param accessToken
     */
    void logout(String accessToken);

    /**
     * @param memberUuid
     * @return UserDetails
     */
    UserDetails loadUserByUsername(String memberUuid);

}

