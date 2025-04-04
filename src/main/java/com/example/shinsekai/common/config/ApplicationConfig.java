package com.example.shinsekai.common.config;

import com.example.shinsekai.common.entity.BaseResponseStatus;
import com.example.shinsekai.common.exception.BaseException;
import com.example.shinsekai.member.infrastructure.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;

@RequiredArgsConstructor
@Configuration
public class ApplicationConfig {

    private final MemberRepository memberRepository;

    /**
     * 사용자 정보를 로드하기 위한 UserDetailsService Bean 정의
     * UUID 기반으로 회원 정보를 조회하며, 존재하지 않을 경우 로그인 실패 예외를 던짐
     *
     * @return UserDetailsService 구현체
     * @throws BaseException 로그인 실패 시 발생
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return memberUuid -> {
            return memberRepository.findByMemberUuid(memberUuid).orElseThrow(
                    () -> new BaseException(BaseResponseStatus.FAILED_TO_LOGIN)
            );
        };
    }

    /**
     * AuthenticationManager Bean 정의
     * 인증 처리를 위해 DaoAuthenticationProvider를 내부에 포함
     *
     * @param authenticationConfiguration Spring Security에서 제공하는 인증 설정
     * @return AuthenticationManager 구현체
     * @throws Exception 구성 중 오류 발생 시
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return new ProviderManager(Arrays.asList(daoAuthenticationProvider()));
    }

    /**
     * Dao 기반 인증 제공자(DaoAuthenticationProvider) Bean 정의
     * 사용자 정보와 비밀번호 비교를 위해 UserDetailsService 및 PasswordEncoder를 설정
     *
     * @return DaoAuthenticationProvider 인스턴스
     */
    @Bean
    public AuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    /**
     * 비밀번호 암호화를 위한 PasswordEncoder Bean
     * BCrypt 알고리즘을 사용하여 비밀번호를 안전하게 해싱
     *
     * @return BCryptPasswordEncoder 인스턴스
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
