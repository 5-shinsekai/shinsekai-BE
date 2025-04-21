package com.example.shinsekai.member.application;

import com.example.shinsekai.agreement.dto.in.MemberAgreementListCreateRequestDto;
import com.example.shinsekai.agreement.infrastructure.AgreementRepository;
import com.example.shinsekai.agreement.infrastructure.MemberAgreementListRepository;
import com.example.shinsekai.common.entity.BaseResponseStatus;
import com.example.shinsekai.common.exception.BaseException;
import com.example.shinsekai.common.jwt.JwtTokenProvider;
import com.example.shinsekai.common.jwt.TokenType;
import com.example.shinsekai.common.redis.RedisProvider;
import com.example.shinsekai.member.dto.in.ChangePasswordRequestDto;
import com.example.shinsekai.member.dto.in.SignInRequestDto;
import com.example.shinsekai.member.dto.in.SignUpRequestDto;
import com.example.shinsekai.member.dto.out.SignInResponseDto;
import com.example.shinsekai.member.entity.Member;
import com.example.shinsekai.member.infrastructure.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisProvider redisProvider;
    private final PasswordEncoder passwordEncoder;
    private final AgreementRepository agreementRepository;
    private final MemberAgreementListRepository memberAgreementListRepository;

    @Override
    @Transactional
    public void signUp(SignUpRequestDto signUpRequestDto) {

        // 아이디 중복 체크
        memberRepository.findByLoginId(signUpRequestDto.getLoginId())
                .ifPresent(member -> {
                    throw new BaseException(BaseResponseStatus.DUPLICATED_LOGIN_ID);
                });

        // 이메일 중복 체크
        memberRepository.findByEmail(signUpRequestDto.getEmail())
                .ifPresent(member -> {
                    throw new BaseException(BaseResponseStatus.DUPLICATED_EMAIL);
                });

        // 휴대전화 번호 중복체크
        memberRepository.findByPhone(signUpRequestDto.getPhone())
                .ifPresent(member -> {
                    throw new BaseException(BaseResponseStatus.DUPLICATED_PHONE);
                });

        // 약관 동의 저장
        List<Long> agreementIdList = signUpRequestDto.getAgreementIdList();
        agreementRepository.findAllById(agreementIdList).stream()
                .map(agreement -> MemberAgreementListCreateRequestDto
                        .of(signUpRequestDto.getMemberUuid(), agreement)
                        .toEntity())
                .forEach(memberAgreementListRepository::save);

        memberRepository.save(signUpRequestDto.toEntity(passwordEncoder));
    }

    @Override
    public SignInResponseDto signIn(SignInRequestDto signInRequestDto) {
        Member member = memberRepository.findByLoginId(signInRequestDto.getLoginId())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.FAILED_TO_LOGIN));

        Authentication authentication = jwtTokenProvider.authenticate(member, signInRequestDto.getPassword());
        if (!authentication.isAuthenticated()) {
            throw new BaseException(BaseResponseStatus.FAILED_TO_LOGIN);
        }

        return jwtTokenProvider.createToken(member);
    }

    @Override
    public void logout() {

        log.info("memberUuid {}", jwtTokenProvider.getMemberUuid());

        // redis에 있는 토큰 정보 삭제
        boolean isDeleteAccess = jwtTokenProvider.deleteToken(TokenType.ACCESS, jwtTokenProvider.getMemberUuid());
        boolean idDeleteRefresh = jwtTokenProvider.deleteToken(TokenType.REFRESH, jwtTokenProvider.getMemberUuid());

        log.info("isDeleteAccess {}", isDeleteAccess);
        log.info("idDeleteRefresh {}", idDeleteRefresh);
    }

    @Override
    public boolean checkId(String loginId) {
        return memberRepository.findByLoginId(loginId).isEmpty();
    }

    @Override
    public String findId(String email, String code) {

        // 레디스에 저장된 값과 다른 경우
        if (!redisProvider.getEmailVerificationCodeForLoginId(email).equals(code)) {
            throw new BaseException(BaseResponseStatus.INVALID_VERIFICATION_CODE);
        }

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_USER));

        return member.getLoginId();
    }

    @Override
    @Transactional
    public void changePassword(ChangePasswordRequestDto changePasswordRequestDto) {

        // 로그인 아이디로 회원 조회
        Member member = memberRepository.findByLoginId(changePasswordRequestDto.getLoginId())
                            .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_USER));

        // 비밀번호 중복 검사
        if (passwordEncoder.matches(changePasswordRequestDto.getNewPassword(), member.getPassword())) {
            throw new BaseException(BaseResponseStatus.DUPLICATED_PASSWORD);
        }

        // 인코딩된 패스워드를 entity에 전달 -> 더티체킹
        member.updatePassword(passwordEncoder.encode(changePasswordRequestDto.getNewPassword()));
    }

}
