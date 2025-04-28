package com.example.shinsekai.email.application;

import com.example.shinsekai.email.dto.in.SendTempRequestDto;
import com.example.shinsekai.email.entity.EmailType;
import com.example.shinsekai.email.dto.in.EmailVerificationRequestDto;
import com.example.shinsekai.email.dto.in.VerificationCodeRequestDto;
import com.example.shinsekai.email.properties.MailProperties;
import com.example.shinsekai.common.entity.BaseResponseStatus;
import com.example.shinsekai.common.exception.BaseException;
import com.example.shinsekai.common.redis.RedisProvider;
import com.example.shinsekai.email.templete.MemberEmailBuilder;
import com.example.shinsekai.email.templete.RestockEmailBuilder;
import com.example.shinsekai.member.entity.Member;
import com.example.shinsekai.member.infrastructure.MemberRepository;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final MemberRepository memberRepository;
    private final JavaMailSender mailSender;
    private final MailProperties mailProperties;
    private final RedisProvider redisProvider;
    private final MemberEmailBuilder memberEmailBuilder;
    private final PasswordEncoder passwordEncoder;
    private final RestockEmailBuilder restockEmailBuilder;

    private final long FIVE_MINUTE = 300000L;

    @Override
    public void sendVerificationEmail(EmailVerificationRequestDto emailVerificationRequestDto) {
        // 메일 타입 꺼내기
        EmailType mailType = emailVerificationRequestDto.getMailType();

        // 인증코드 생성
        String verificationCode = generateVerificationCode();

        // 메일을 html로 만들고 보낸다.
        buildAndSend(emailVerificationRequestDto.getEmail(), mailType, verificationCode);

        // redis에 데이터를 저장한다.
        saveCode(emailVerificationRequestDto.getEmail(), mailType, verificationCode);
    }

    @Override
    public void verifyCode(VerificationCodeRequestDto verificationCodeRequestDto) {

        switch (verificationCodeRequestDto.getMailType()) {

            case FIND_LOGIN_ID -> {

                memberRepository.findByEmail(verificationCodeRequestDto.getEmail())
                        .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_USER));

                if (!verificationCodeRequestDto.getCode()
                        .equals(redisProvider.getEmailVerificationCodeForLoginId(
                                verificationCodeRequestDto.getEmail()
                        ))) {
                    throw new BaseException(BaseResponseStatus.INVALID_VERIFICATION_CODE);
                }
            }
            case SIGN_UP -> {
                if (!verificationCodeRequestDto.getCode()
                        .equals(redisProvider.getEmailVerificationCodeForSignUp(
                                verificationCodeRequestDto.getEmail()
                        ))) {
                    throw new BaseException(BaseResponseStatus.INVALID_VERIFICATION_CODE);
                }
            }
            default -> {
                throw new BaseException(BaseResponseStatus.FAILED_TO_SEND_EMAIL);
            }
        }
    }

    @Override
    @Transactional
    public void sendTempPassword(SendTempRequestDto sendTempRequestDto) {

        Member member = memberRepository
                .findByLoginIdAndEmail(sendTempRequestDto.getLoginId(), sendTempRequestDto.getEmail())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_USER));

        // 임시 비밀번호 생성
        String tempPw = generateTempPassword();

        // 메일을 html로 만들고 보낸다.
        buildAndSend(sendTempRequestDto.getEmail(), EmailType.TEMP_PW, tempPw);

        // 임시 비밀번호 저장
        member.saveTempPassword(passwordEncoder.encode(tempPw));
    }

    @Override
    @Transactional
    public void sendRestockEmail(String toEmail, String productName) {
        memberRepository.findByEmail(toEmail)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.FAILED_TO_SEND_EMAIL));
        buildAndSend(toEmail, EmailType.RESTOCK_NOTIFY, productName);
    }


    /**
     * 이메일을 실제로 전송하며, 전송 성공 시 Redis에 인증코드를 저장
     *
     * @param email 수신자 이메일
     * @throws BaseException 이메일 전송 실패 시 예외 발생
     */
    private void buildAndSend(String email, EmailType mailType, String item) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(new InternetAddress(mailProperties.getUsername(), "스타벅스 스토어", "UTF-8"));
            helper.setTo(email);
            helper.setSubject(setSubject(mailType));

            String html;

            switch (mailType) {
                case SIGN_UP -> {
                    html = memberEmailBuilder.buildMemberEmail("회원가입 인증 코드 안내",
                            "본인 확인을 위해 아래 인증 코드를 입력해주세요.",
                            item,
                            "코드는 5분 동안 유효합니다.");  // 인증번호
                }
                case FIND_LOGIN_ID -> {
                    html = memberEmailBuilder.buildMemberEmail("아이디 찾기 인증 코드 안내",
                            "본인 확인을 위해 아래 인증 코드를 입력해주세요.",
                            item,
                            "코드는 5분 동안 유효합니다.");  // 인증번호
                }
                case TEMP_PW -> {
                    html = memberEmailBuilder.buildMemberEmail("임시 비밀번호 안내",
                            "요청하신 임시 비밀번호가 아래와 같이 발급되었습니다.",
                            item,
                            "로그인 후 즉시 비밀번호를 변경하시기 바랍니다.");  // 임시 비밀번호
                }
                case RESTOCK_NOTIFY -> {
                    html = restockEmailBuilder.buildRestockNotifyEmail(item); // item: 상품명
                }
                default -> {
                    throw new BaseException(BaseResponseStatus.FAILED_TO_SEND_EMAIL);
                }
            }

            helper.setText(html, true);

            mailSender.send(message);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void saveCode(String email, EmailType mailType, String code) {
        // 메일 타입에 따른 redis 저장
        switch (mailType) {
            case FIND_LOGIN_ID -> {
                redisProvider.setEmailVerificationCodeForLoginId(email, code, FIVE_MINUTE);
                break;
            }
            case SIGN_UP -> {
                redisProvider.setEmailVerificationCodeForSignUp(email, code, FIVE_MINUTE);
                break;
            }
            default -> {
                throw new BaseException(BaseResponseStatus.FAILED_TO_SEND_EMAIL);
            }
        }
    }

    /**
     * 이메일 타입에 따라 제목을 설정
     *
     * @param mailType 이메일 유형 (SIGN_UP, FIND_LOGIN_ID, TEMP_PW)
     * @return 이메일 제목 문자열
     */
    private String setSubject(EmailType mailType) {
        String subject = "";

        switch (mailType) {
            case SIGN_UP -> {
                subject = "회원가입을 위한 인증코드 안내메일";
                break;
            }
            case FIND_LOGIN_ID -> {
                subject = "아이디 찾기를 위한 인증코드 안내메일";
                break;
            }
            case TEMP_PW -> {
                subject = "임시 비밀번호 발급 안내메일";
                break;
            }
            case RESTOCK_NOTIFY -> {
                subject = "재입고 알림 안내 메일";
                break;
            }
            default -> {
                throw new BaseException(BaseResponseStatus.FAILED_TO_SEND_EMAIL);
            }
        }

        return "[스타벅스] " + subject;
    }

    /**
     * 6자리 숫자 형식의 랜덤 인증코드를 생성합니다.
     *
     * @return 6자리 인증코드 문자열
     */
    private String generateVerificationCode() {
        SecureRandom random = new SecureRandom();
        int code = 100000 + random.nextInt(900000); // 100000 ~ 999999 범위
        return String.valueOf(code);
    }

    /**
     * 8자리(영어소문자+숫자+특수문자) 랜덤 임시 비밀번호를 생성합니다.
     *
     * @return 8자리 임시 비밀번호 문자열
     */
    private String generateTempPassword() {
        String lowerCase = "abcdefghijklmnopqrstuvwxyz";
        String digits = "0123456789";
        String special = "!@#$%^&*";
        String allChars = lowerCase + digits + special;

        Random random = new Random();
        List<Character> passwordChars = new ArrayList<>();

        // 각각 최소 1개 포함
        passwordChars.add(lowerCase.charAt(random.nextInt(lowerCase.length())));
        passwordChars.add(digits.charAt(random.nextInt(digits.length())));
        passwordChars.add(special.charAt(random.nextInt(special.length())));

        // 나머지 5자리는 전체에서 랜덤하게 추가
        for (int i = 0; i < 5; i++) {
            passwordChars.add(allChars.charAt(random.nextInt(allChars.length())));
        }

        // 순서 섞기
        Collections.shuffle(passwordChars);

        // 문자 리스트를 String으로 변환
        StringBuilder password = new StringBuilder();
        for (char c : passwordChars) {
            password.append(c);
        }

        return password.toString();
    }
}
