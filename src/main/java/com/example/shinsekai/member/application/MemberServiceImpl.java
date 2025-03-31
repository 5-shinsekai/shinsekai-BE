package com.example.shinsekai.member.application;

import com.example.shinsekai.common.entity.BaseResponseStatus;
import com.example.shinsekai.common.exception.BaseException;
import com.example.shinsekai.common.jwt.JwtTokenProvider;
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

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

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
        Member member = memberRepository.findByLoginId(signInRequestDto.getLoginId())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.FAILED_TO_LOGIN));

        try {
            Authentication authentication = authenticate(member, signInRequestDto.getPassword());
            return createToken(authentication, member);
        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.FAILED_TO_LOGIN);
        }
    }



    @Override
    public UserDetails loadUserByUsername (String memberUuid) {
        return memberRepository.findByMemberUuid(memberUuid).orElseThrow(() -> new IllegalArgumentException(memberUuid));
    }

    private SignInResponseDto createToken(Authentication authentication, Member member) {
        String accessToken = jwtTokenProvider.generateAccessToken(authentication);
        String refreshToken = jwtTokenProvider.generateRefreshToken();

        return SignInResponseDto.from(member, accessToken, refreshToken);
    }


    private Authentication authenticate(Member member, String inputPassword) {

        if (!passwordEncoder.matches(inputPassword, member.getPassword())) {
            throw new BaseException(BaseResponseStatus.FAILED_TO_LOGIN);
        }

        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                member.getLoginId(),
                inputPassword
        ));
    }

}
