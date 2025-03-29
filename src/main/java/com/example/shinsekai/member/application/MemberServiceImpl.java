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
            System.out.println("있음");
        }else {
            System.out.println("없음!");
            new BaseException(BaseResponseStatus.FAILED_TO_LOGIN);
        }

        try {
            Authentication auth = authenticate(member.get(), signInRequestDto.getPassword());
            System.out.println("auth = " + auth);
            String token = createToken(auth);
            System.out.println("tokent = " + token);

            return SignInResponseDto.from(member.get(), token);

        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.FAILED_TO_LOGIN);
        }
    }

    @Override
    public UserDetails loadUserByUsername (String memberUuid) {
        //(String memberUuid) {
        //return memberRepository.findByLoginId(loginId).orElseThrow(() -> new IllegalArgumentException(loginId));
        return memberRepository.findByMemberUuid(memberUuid).orElseThrow(() -> new IllegalArgumentException(memberUuid));
    }

    private String createToken(Authentication authentication) {
        return jwtTokenProvider.generateAccessToken(authentication);
    }

    private Authentication authenticate(Member member, String inputPassword) {
        System.out.println("여기!!라고!!");
        System.out.println("member = " + member);
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        member.getLoginId(),
                        inputPassword
                )
        );
    }
}
