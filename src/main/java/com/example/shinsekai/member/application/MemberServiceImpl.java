package com.example.shinsekai.member.application;

import com.example.shinsekai.common.entity.BaseResponseStatus;
import com.example.shinsekai.common.exception.BaseException;
import com.example.shinsekai.common.jwt.JwtTokenProvider;
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
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisProvider redisProvider;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void signUp(SignUpRequestDto signUpRequestDto) {

        // 아이디 중복 체크
        memberRepository.findByLoginId(signUpRequestDto.getLoginId())
                .ifPresent(member -> {
                    throw new BaseException(BaseResponseStatus.SAME_LOGIN_ID);
                });

        // 이메일 중복 체크
        memberRepository.findByEmail(signUpRequestDto.getEmail())
                .ifPresent(member -> {
                    throw new BaseException(BaseResponseStatus.SAME_EMAIL);
                });

        // 닉네임 중복 체크
        memberRepository.findByNickname(signUpRequestDto.getNickname())
                .ifPresent(member -> {
                    throw new BaseException(BaseResponseStatus.SAME_NICKNAME);
                });

        // 휴대전화 번호 중복체크
        memberRepository.findByPhone(signUpRequestDto.getPhone())
                .ifPresent(member -> {
                    throw new BaseException(BaseResponseStatus.SAME_PHONE);
                });

        memberRepository.save(signUpRequestDto.toEntity(passwordEncoder));

    }

    @Override
    public SignInResponseDto signIn(SignInRequestDto signInRequestDto) {
        Member member = memberRepository.findByLoginId(signInRequestDto.getLoginId())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.FAILED_TO_LOGIN));

        if (!passwordEncoder.matches(signInRequestDto.getPassword(), member.getPassword())) {
            throw new BaseException(BaseResponseStatus.FAILED_TO_LOGIN);
        }

        Authentication authentication = jwtTokenProvider.authenticate(member, signInRequestDto.getPassword());
        return jwtTokenProvider.createToken(authentication, member);
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
    @Transactional
    public void changePassword(ChangePasswordRequestDto changePasswordRequestDto) {
        try {
            // 토큰 검증
            jwtTokenProvider.extractAllClaims(changePasswordRequestDto.getAccessToken());
        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.TOKEN_NOT_VALID);
        }

        // 로그인 아이디로 회원 조회
        Member member = memberRepository.findByLoginId(changePasswordRequestDto.getLoginId())
                            .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_USER));

        // 비밀번호 중복 검사
        if (passwordEncoder.matches(changePasswordRequestDto.getNewPassword(), member.getPassword())) {
            throw new BaseException(BaseResponseStatus.DUPLICATED_PASSWORD);
        }

        // 비밀번호 형식 검사
        if (!validatePassword(changePasswordRequestDto.getNewPassword())) {
            throw new BaseException(BaseResponseStatus.INVALID_PASSWORD_FORMAT);
        }

        // 인코딩된 패스워드를 entity에 전달 -> 더티체킹
        member.updatePassword(passwordEncoder.encode(changePasswordRequestDto.getNewPassword()));
    }

    private boolean validatePassword(String password) {
        // PASSWORD_REGEX → 유효성 체크할 정규식(Regex) 패턴 정의.
        // 공백 허용 안 됨
        // 소문자 (?=.*[a-z])
        // 숫자 (?=.*\\d)
        // 특수문자 (?=.*[@$!%*?&])
        // 전체 길이 8~20자
        final String PASSWORD_REGEX =
                "^(?=\\S+$)(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[a-z\\d@$!%*?&]{8,20}$";

        if (password == null) {
            return false;
        } else {
            return password.matches(PASSWORD_REGEX);
        }
    }
}
