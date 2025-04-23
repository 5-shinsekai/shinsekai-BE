package com.example.shinsekai.email.templete;

import org.springframework.stereotype.Component;

@Component
public class RestockEmailBuilder {
    public String buildRestockNotifyEmail(String productName){
        return """
            <html lang="ko">
                <body style="font-family: Arial, sans-serif; margin: 0; padding: 0; background-color: #f4f4f4;">
                    <div style="width: 100%%; max-width: 600px; margin: 0 auto; background-color: #ffffff; padding: 20px; border-radius: 10px; box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1); text-align: center;">
                        <img src="https://www.starbucks.co.kr/common/img/common/logo.png" alt="스타벅스 로고" style="width: 120px; margin: 20px 0;" />
                        
                        <p style="font-size: 24px; font-weight: bold; color: #00704A;">[스타벅스 스토어] 상품 재입고 알림</p>
                        
                        <p style="font-size: 16px; color: #333333; margin: 20px 0;">
                            안녕하세요!
                        </p>
                        
                        <p>고객님께서 신청하신 상품 <strong>%s</strong> 이(가) 재입고되었습니다 🎉</p>
                        <p>지금 바로 확인해보세요!</p>
                        <br>
                        
                        <p style="font-size: 16px; color: #333333; margin: 20px 0;">
                            ※ 이 메일은 알림 수신 신청에 따라 발송된 안내 메일입니다.
                        </p>
                        
                        <a href="https://spharos.shop/main" style="display: inline-block; background-color: #00704A; color: #ffffff; padding: 12px 20px; text-decoration: none; border-radius: 5px; font-size: 16px; margin-top: 20px;">
                            상품 보러가기
                        </a>
                        
                        <p style="font-size: 12px; color: #888888; margin-top: 30px;">
                            본 메일은 스파로스 아카데미 6기 1차 프로젝트를 위해 개발되었습니다. <br />
                        </p>
                    </div>
                </body>
            </html>
            """.formatted(productName);
    }
}
