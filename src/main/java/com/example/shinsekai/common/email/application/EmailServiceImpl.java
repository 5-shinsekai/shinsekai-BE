package com.example.shinsekai.common.email.application;

import com.example.shinsekai.common.email.EmailEnum;
import com.example.shinsekai.common.email.dto.in.EmailVerificationRequestDto;
import com.example.shinsekai.common.email.dto.in.VerificationCodeRequestDto;
import com.example.shinsekai.common.email.properties.MailProperties;
import com.example.shinsekai.common.entity.BaseResponseStatus;
import com.example.shinsekai.common.exception.BaseException;
import com.example.shinsekai.common.redis.RedisProvider;
import com.example.shinsekai.member.entity.Member;
import com.example.shinsekai.member.infrastructure.MemberRepository;
import jakarta.mail.MessagingException;
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

    private final long FIVE_MINUTE = 300000L;

    private String verificationCode;

    /**
     * 이메일 인증 요청을 처리하며, 인증코드를 생성해 해당 이메일로 발송합니다.
     *
     * @param emailVerificationRequestDto 이메일 인증 요청 정보 (email)
     * @param mailType                    인증 메일 타입 (FIND_LOGIN_ID, CHANGE_PASSWORD 등)
     * @throws BaseException              해당 이메일로 등록된 회원이 없을 경우 예외 발생
     */
    @Override
    public void sendVerificationEmail(EmailVerificationRequestDto emailVerificationRequestDto, EmailEnum mailType) {

        Member member = memberRepository.findByEmail(emailVerificationRequestDto.getEmail())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_USER));

        verificationCode = generateVerificationCode();

        String subject = this.setSubject(mailType);
        String body = this.setBody(member, verificationCode);
        sendEmail(member.getEmail(), subject, body);
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

        if (!verificationCodeRequestDto.getCode()
                .equals(redisProvider.getEmailVerificationCode(verificationCodeRequestDto.getEmail()))) {
            throw new BaseException(BaseResponseStatus.INVALID_VERIFICATION_CODE);
        }

    }

    /**
     * 이메일을 실제로 전송하며, 전송 성공 시 Redis에 인증코드를 저장
     *
     * @param email   수신자 이메일 주소
     * @param subject 이메일 제목
     * @param body    이메일 본문 (인증코드 포함)
     * @throws BaseException 이메일 전송 실패 시 예외 발생
     */
    private void sendEmail(String email, String subject, String body) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            // sMailProperties에서 동적으로 발신자 정보 가져오기
            helper.setFrom(mailProperties.getUsername());
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(body);

            mailSender.send(message);

            redisProvider.setEmailVerificationCode(email, verificationCode, FIVE_MINUTE);
        } catch (MessagingException e) {
            throw new BaseException(BaseResponseStatus.FAILED_TO_SEND_EMAIL);
        }
    }

    /**
     * 이메일 타입에 따라 제목을 설정
     *
     * @param mailType 이메일 유형 (FIND_LOGIN_ID, CHANGE_PASSWORD 등)
     * @return 이메일 제목 문자열
     */
    private String setSubject(EmailEnum mailType) {
        switch (mailType) {
            case FIND_LOGIN_ID:
                return "[스타벅스] 아이디 찾기를 위한 인증코드 안내메일";
            case CHANGE_PASSWORD:
                return "[스타벅스] 비밀번호 변경을 위한 인증코드 안내메일";
            default:
                return "[스타벅스] 안내메일";
        }
    }

    /**
     * 수신자 정보와 인증코드를 포함한 이메일 본문을 구성합니다.
     *
     * @param member           수신 대상 회원 정보
     * @param verificationCode 생성된 인증코드
     * @return 이메일 본문 문자열
     */
    private String setBody(Member member, String verificationCode) {

        StringBuffer sb = new StringBuffer();
        sb.append("본인확인 인증코드 입니다.\n");
        sb.append(member.getName() + " 고객님\n");
        sb.append("본인 확인을 위해 아래의 인증코드를 화면에 입력해주세요.\n");
        sb.append("\n");
        sb.append(verificationCode + "\n");
        sb.append("\n");
        sb.append("*인증코드는 5분 동안만 유효합니다.\n");
        sb.append("*본 이메일은 스파로스 아카데미 1차 프로젝트를 위해 전송된 메일입니다.");
        return sb.toString();
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
