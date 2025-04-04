package com.example.shinsekai.member.application;

import com.example.shinsekai.common.entity.BaseResponseStatus;
import com.example.shinsekai.common.exception.BaseException;
import com.example.shinsekai.common.jwt.JwtTokenProvider;
import com.example.shinsekai.common.jwt.TokenEnum;
import com.example.shinsekai.common.redis.RedisProvider;
import com.example.shinsekai.member.dto.in.ChangePasswordRequestDto;
import com.example.shinsekai.member.dto.in.SignInRequestDto;
import com.example.shinsekai.member.dto.in.SignUpRequestDto;
import com.example.shinsekai.member.dto.out.FindIdResponseDto;
import com.example.shinsekai.member.dto.out.SignInResponseDto;
import com.example.shinsekai.member.entity.Member;
import com.example.shinsekai.member.infrastructure.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisProvider redisProvider;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void signUp(SignUpRequestDto signUpRequestDto) {
        try {
            memberRepository.save(signUpRequestDto.toEntity(passwordEncoder));
        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.FAILED_TO_RESTORE);
        }
    }

    @Override
    public SignInResponseDto signIn(SignInRequestDto signInRequestDto) {
        Member member = memberRepository.findByLoginId(signInRequestDto.getLoginId())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.FAILED_TO_LOGIN));
        try {
            Authentication authentication = jwtTokenProvider.authenticate(member, signInRequestDto.getPassword());
            return jwtTokenProvider.createToken(authentication, member);
        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.FAILED_TO_LOGIN);
        }
    }

    @Override
    public void logout(String accessToken) {
        // redis에서 토큰 삭제
        boolean deleteRefreshTokenSuccess
                = redisProvider.deleteValue(jwtTokenProvider.extractAllClaims(accessToken).getSubject());
        if(!deleteRefreshTokenSuccess)
            throw new BaseException(BaseResponseStatus.FAILED_TO_RESTORE);
    }

    @Override
    public FindIdResponseDto findId(String email, boolean emailVerified) {
        if (!emailVerified) {
            new BaseException(BaseResponseStatus.INVALID_VERIFICATION_CODE);
        }

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_USER));

        return new FindIdResponseDto().from(member);
    }

    @Override
    public void changePassword(ChangePasswordRequestDto changePasswordRequestDto) {
        try {
            // 토큰 검증
            jwtTokenProvider.extractAllClaims(changePasswordRequestDto.getAccessToken());
        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.TOKEN_NOT_VALID);
        }

        Member member = memberRepository.findByLoginId(changePasswordRequestDto.getLoginId())
                            .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_USER));

        if (!passwordEncoder.matches(changePasswordRequestDto.getNewPassword(), member.getPassword())) {
            throw new BaseException(BaseResponseStatus.DUPLICATED_PASSWORD);
        }

        member.updatePassword(changePasswordRequestDto);
        memberRepository.save(member);
    }

}
