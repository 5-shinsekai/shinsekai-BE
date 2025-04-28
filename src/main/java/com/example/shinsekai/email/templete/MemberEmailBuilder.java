package com.example.shinsekai.email.templete;

import org.springframework.stereotype.Component;

@Component
public class MemberEmailBuilder {

    public String buildMemberEmail(String subject, String description, String code, String warning) {
        return """
                <html lang="ko">
                    <body style="font-family: Arial, sans-serif; margin: 0; padding: 0; background-color: #f4f4f4;">
                        <div style="width: 100%%; max-width: 600px; margin: 0 auto; background-color: #ffffff; padding: 20px; border-radius: 10px; box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1); text-align: center;">
                            <img src="https://www.starbucks.co.kr/common/img/common/logo.png" alt="스타벅스 로고" style="width: 120px; margin: 20px 0;" />
                
                            <p style="font-size: 24px; font-weight: bold; color: #00704A;">%s</p>
                
                            <p style="font-size: 16px; color: #333333; margin: 20px 0;">
                                %s
                            </p>
                
                            <p style="font-size: 28px; font-weight: bold; color: #00704A; background-color: #e3f2ed; padding: 10px; border-radius: 5px; display: inline-block; margin: 20px 0;">
                                %s
                            </p>
                
                            <p style="font-size: 16px; color: #333333; margin: 20px 0;">
                               <strong>%s</strong> 
                            </p>
                
                            <a href="https://spharos.shop/main" style="display: inline-block; background-color: #00704A; color: #ffffff; padding: 12px 20px; text-decoration: none; border-radius: 5px; font-size: 16px; margin-top: 20px;">
                                스타벅스 스토어 방문
                            </a>
                
                            <p style="font-size: 12px; color: #888888; margin-top: 30px;">
                                본 메일은 스파로스 아카데미 6기 1차 프로젝트를 위해 개발되었습니다. <br />
                            </p>
                        </div>
                    </body>
                </html>
                """.formatted(subject, description, code, warning);
    }
}
