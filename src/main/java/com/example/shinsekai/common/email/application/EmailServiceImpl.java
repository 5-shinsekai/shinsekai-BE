package com.example.shinsekai.common.email.application;

import com.example.shinsekai.common.email.EmailEnum;
import com.example.shinsekai.common.email.dto.in.EmailAuthRequestDto;
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
import java.util.Optional;

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

    @Override
    public void sendVerificationEmail(EmailAuthRequestDto emailAuthRequestDto) {

        try {
            Member member = memberRepository.findByEmail(emailAuthRequestDto.getEmail())
                    .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_USER));

            verificationCode = generateVerificationCode();

            String subject = this.setSubject(EmailEnum.FIND_LOGIN_ID);
            String body = this.setBody(member, verificationCode);
            sendEmail(member.getEmail(), subject, body);

        } catch (BaseException e) {
            throw new RuntimeException(e);
        }
    }

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
            throw new RuntimeException(e);
        }
    }

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

    // 랜덤 6자리 숫자 생성
    private String generateVerificationCode() {
        SecureRandom random = new SecureRandom();
        int code = 100000 + random.nextInt(900000); // 100000 ~ 999999 범위
        return String.valueOf(code);
    }
}
