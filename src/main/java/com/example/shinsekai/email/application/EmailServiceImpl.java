package com.example.shinsekai.email.application;

import com.example.shinsekai.email.entity.EmailType;
import com.example.shinsekai.email.dto.in.EmailVerificationRequestDto;
import com.example.shinsekai.email.dto.in.VerificationCodeRequestDto;
import com.example.shinsekai.email.properties.MailProperties;
import com.example.shinsekai.common.entity.BaseResponseStatus;
import com.example.shinsekai.common.exception.BaseException;
import com.example.shinsekai.common.redis.RedisProvider;
import com.example.shinsekai.member.entity.Member;
import com.example.shinsekai.member.infrastructure.MemberRepository;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService{

    private final MemberRepository memberRepository;
    private final JavaMailSender mailSender;
    private final MailProperties mailProperties;
    private final RedisProvider redisProvider;
    private final VerificationEmailBuilder verificationEmailBuilder;

    private final long FIVE_MINUTE = 300000L;
    private String verificationCode;

    /**
     * 이메일 인증 요청을 처리하며, 인증코드를 생성해 해당 이메일로 발송합니다.
     *
     * @param emailVerificationRequestDto 이메일 인증 요청 정보 (email)
     * @throws BaseException              해당 이메일로 등록된 회원이 없을 경우 예외 발생
     */
    @Override
    public void sendVerificationEmail(EmailVerificationRequestDto emailVerificationRequestDto) {
        Member member = memberRepository.findByEmail(emailVerificationRequestDto.getEmail())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_USER));

        verificationCode = generateVerificationCode();
        sendEmail(member, this.setSubject(emailVerificationRequestDto.getMailType()));

        switch (emailVerificationRequestDto.getMailType()) {
            case FIND_LOGIN_ID -> {
                redisProvider.setEmailVerificationCodeForLoginId(emailVerificationRequestDto.getEmail(), verificationCode, FIVE_MINUTE);
                break;
            }
            case CHANGE_PASSWORD -> {
                redisProvider.setEmailVerificationCodeForPw(emailVerificationRequestDto.getEmail(), verificationCode, FIVE_MINUTE);
                break;
            }
            default -> {
                return;
            }
        }
    }

    /**
     * 사용자가 입력한 인증코드가 유효한지 Redis에 저장된 값과 비교하여 검증합니다.
     *
     * @param verificationCodeRequestDto 인증코드 검증 요청 정보 (email + code)
     * @throws BaseException              회원이 존재하지 않거나, 인증코드가 일치하지 않을 경우 예외 발생
     */
    @Override
    public void verifyCode(VerificationCodeRequestDto verificationCodeRequestDto) {
        memberRepository.findByEmail(verificationCodeRequestDto.getEmail())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_USER));

        // 아이디 찾기
        if (verificationCodeRequestDto.getMailType().equals(EmailType.FIND_LOGIN_ID)) {
            if (!verificationCodeRequestDto.getCode()
                    .equals(redisProvider.getEmailVerificationCodeForLoginId(verificationCodeRequestDto.getEmail()))) {
                throw new BaseException(BaseResponseStatus.INVALID_VERIFICATION_CODE);
            }
        }
        // 비밀번호 찾기
        else if (verificationCodeRequestDto.getMailType().equals(EmailType.CHANGE_PASSWORD)) {
            if (!verificationCodeRequestDto.getCode()
                    .equals(redisProvider.getEmailVerificationCodeForPw(verificationCodeRequestDto.getEmail()))) {
                throw new BaseException(BaseResponseStatus.INVALID_VERIFICATION_CODE);
            }
        }

    }

    /**
     * 이메일을 실제로 전송하며, 전송 성공 시 Redis에 인증코드를 저장
     *
     * @param member  사용자 정보
     * @param subject 이메일 제목
     * @throws BaseException 이메일 전송 실패 시 예외 발생
     */
    private void sendEmail(Member member, String subject) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(new InternetAddress(mailProperties.getUsername(), "스타벅스 스토어", "UTF-8"));
            helper.setTo(member.getEmail());
            helper.setSubject(subject);

            String html
                    = verificationEmailBuilder.buildVerificationEmail(member.getName(), verificationCode);

            helper.setText(html, true);

            mailSender.send(message);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 이메일 타입에 따라 제목을 설정
     *
     * @param mailType 이메일 유형 (FIND_LOGIN_ID, CHANGE_PASSWORD 등)
     * @return 이메일 제목 문자열
     */
    private String setSubject(EmailType mailType) {
        String subject = "";
        switch (mailType) {
            case FIND_LOGIN_ID:
                subject = "아이디 찾기"; break;
            case CHANGE_PASSWORD:
                subject = "비밀번호 변경"; break;
            default:
                subject = "";
        }

        return "[스타벅스] " + subject + "(을)를 위한 인증코드 안내메일";
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
}
