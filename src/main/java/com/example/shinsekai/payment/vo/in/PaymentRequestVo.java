package com.example.shinsekai.payment.vo.in;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class PaymentRequestVo {
    private String orderName;
    private Double paymentPrice;
    private String paymentMethod;
    private String status;
    private String receiptUrl;

    //스타벅스 카드
    private String memberStarbucksCardUuid;
}
