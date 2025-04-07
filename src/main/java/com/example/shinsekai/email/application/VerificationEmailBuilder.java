package com.example.shinsekai.email.application;

import org.springframework.stereotype.Component;

@Component
public class VerificationEmailBuilder {
    public String buildVerificationEmail(String name, String code) {
        return """
            <html lang="ko">
                <body style="font-family: Arial, sans-serif; margin: 0; padding: 0; background-color: #f4f4f4;">
                    <div style="width: 100%%; max-width: 600px; margin: 0 auto; background-color: #ffffff; padding: 20px; border-radius: 10px; box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1); text-align: center;">
                        <img src="https://www.starbucks.co.kr/common/img/common/logo.png" alt="스타벅스 로고" style="width: 120px; margin: 20px 0;" />
                        
                        <p style="font-size: 24px; font-weight: bold; color: #00704A;">스타벅스 인증 코드 안내</p>
                        
                        <p style="font-size: 16px; color: #333333; margin: 20px 0;">
                            안녕하세요, <strong>%s</strong> 님!<br />
                            본인 확인을 위해 아래 인증 코드를 입력해주세요.
                        </p>
                        
                        <p style="font-size: 28px; font-weight: bold; color: #00704A; background-color: #e3f2ed; padding: 10px; border-radius: 5px; display: inline-block; margin: 20px 0;">
                            %s
                        </p>
                        
                        <p style="font-size: 16px; color: #333333; margin: 20px 0;">
                            코드는 <strong>5분 동안</strong> 유효합니다.
                        </p>
                        
                        <a href="https://www.spharosacademy.com/" style="display: inline-block; background-color: #00704A; color: #ffffff; padding: 12px 20px; text-decoration: none; border-radius: 5px; font-size: 16px; margin-top: 20px;">
                            스파로스 아카데미 홈페이지 방문
                        </a>
                        
                        <p style="font-size: 12px; color: #888888; margin-top: 30px;">
                            본 메일은 스파로스 아카데미 6기 1차 프로젝트를 위해 개발되었습니다. <br />
                        </p>
                    </div>
                </body>
            </html>
            """.formatted(name, code);
    }
}
