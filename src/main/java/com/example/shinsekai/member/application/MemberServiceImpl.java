package com.example.shinsekai.member.application;

import com.example.shinsekai.common.jwt.JwtTokenProvider;
import com.example.shinsekai.member.dto.in.MemberAddDto;
import com.example.shinsekai.member.dto.in.SignInRequestDto;
import com.example.shinsekai.member.dto.in.SignUpRequestDto;
import com.example.shinsekai.member.dto.out.SignInResponseDto;
import com.example.shinsekai.member.entity.Member;
import com.example.shinsekai.member.infrastructure.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    @Override
    public void addMember(MemberAddDto memberAddDto) {
        memberRepository.save(memberAddDto.toEntity(UUID.randomUUID().toString()));
    }

    @Override
    public void signUp(SignUpRequestDto signUpRequestDto) {
//        try {
//            memberRepository.save(signUpRequestDto.toEntity(UUID.randomUUID().toString()));
//        } catch (Exception e) {
//            throw new BaseException(BaseResponseStatus.FAILED_TO_RESTORE);
//        }
    }

    @Override
    public SignInResponseDto signIn(SignInRequestDto signInRequestDto) {

//        Member member = memberRepository.findByEmail(signInRequestDto.getEmail()).orElseThrow(
//                () -> new BaseException(BaseResponseStatus.FAILED_TO_LOGIN)
//        );
//
//        try {
//            return SignInResponseDto.from(member, createToken(authenticate(member, signInRequestDto.getPassword())));
//
//        } catch (Exception e) {
//            throw new BaseException(BaseResponseStatus.FAILED_TO_LOGIN);
//        }

        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String memberUuid) {
        return memberRepository.findByMemberUuid(memberUuid).orElseThrow(() -> new IllegalArgumentException(memberUuid));
    }

    private String createToken(Authentication authentication) {
        return jwtTokenProvider.generateAccessToken(authentication);
    }

    private Authentication authenticate(Member member, String inputPassword) {
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        member.getEmail(),
                        inputPassword
                )
        );
    }
}
