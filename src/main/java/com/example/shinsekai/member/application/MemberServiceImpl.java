package com.example.shinsekai.member.application;

import com.example.shinsekai.common.entity.BaseResponseStatus;
import com.example.shinsekai.common.exception.BaseException;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void addMember(MemberAddDto memberAddDto) {
//        memberRepository.save(memberAddDto.toEntity(UUID.randomUUID().toString()));
    }

    @Override
    public void signUp(SignUpRequestDto signUpRequestDto) {
        try {
            memberRepository.save(signUpRequestDto.toEntity(new BCryptPasswordEncoder()));
        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.FAILED_TO_RESTORE);
        }
    }

    @Override
    public SignInResponseDto signIn(SignInRequestDto signInRequestDto) {

        System.out.println("signInRequestDto.getLoginId() = " + signInRequestDto.getLoginId());

        Optional<Member> member = memberRepository.findByLoginId(signInRequestDto.getLoginId());
        if(member.isEmpty()) {
            System.out.println("exist!");
        }else {
            System.out.println("not exist!!");
            new BaseException(BaseResponseStatus.FAILED_TO_LOGIN);
        }

        try {
            Authentication auth = authenticate(member.get(), signInRequestDto.getPassword());
            System.out.println("auth = " + auth);
            String token = createToken(auth);
            System.out.println("token = " + token);

            return SignInResponseDto.from(member.get(), token);

        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.FAILED_TO_LOGIN);
        }
    }

    @Override
    public UserDetails loadUserByUsername (String memberUuid) {
        System.out.println("loadUserByUsername!");
        return memberRepository.findByMemberUuid(memberUuid).orElseThrow(() -> new IllegalArgumentException(memberUuid));
    }

    private String createToken(Authentication authentication) {
        System.out.println("create!!");
        System.out.println("authentication.getPrincipal() = " + authentication.getPrincipal());
        return jwtTokenProvider.generateAccessToken(authentication);
    }

//    private Authentication authenticate(Member member, String inputPassword) {
//        System.out.println("authenticate!!");
//        System.out.println("member = " + member);
//        System.out.println("inputPassword = " + inputPassword);
//
//        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
//                                                                        member.getLoginId(),
//                                                                        inputPassword
//                                                                );
//
//        System.out.println("authToken.getCredentials() = " + authToken.getCredentials());
//        System.out.println("authToken.getPrincipal() = " + authToken.getPrincipal());
//
//        Authentication auth = authenticationManager.authenticate(authToken);
//
//        System.out.println("auth.getAuthorities() = " + auth.getAuthorities());
//        System.out.println("auth.isAuthenticated() = " + auth.isAuthenticated());
//        System.out.println("auth.toString() = " + auth.toString());
//        System.out.println("auth.getName() = " + auth.getName());
//        System.out.println("auth.getDetails() = " + auth.getDetails());
//        System.out.println("auth.getCredentials() = " + auth.getCredentials());
//        System.out.println("auth.getPrincipal() = " + auth.getPrincipal());
//        return auth;
//    }

    private Authentication authenticate(Member member, String inputPassword) {
        System.out.println("authenticate!!");
        System.out.println("member = " + member);
        System.out.println("inputPassword = " + inputPassword);

        boolean trueOrFalse = passwordEncoder.matches(inputPassword, member.getPassword());
        System.out.println("trueOrFalse = " + trueOrFalse);
        
        if (!trueOrFalse) {
            throw new BaseException(BaseResponseStatus.FAILED_TO_LOGIN);
        }

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                member.getLoginId(),
                inputPassword
        );

        return authenticationManager.authenticate(authToken);
    }

}
