package com.example.shinsekai.payment.vo.in;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class PaymentRequestVo {
    private String paymentKey;
    private String orderName;
    private Double paymentPrice;
    private String paymentMethod;
    private String status;
    private String receiptUrl;
    private LocalDateTime approvedAt;

    //간편결제
    private String cardName;
    private String cardNumber;
    private String approveNo;
    private boolean isInterestFree;
    private int installmentPlanMonths;
    private boolean useCardPoint;

    //스타벅스 카드
    private String memberStarbucksCardUuid;
}
