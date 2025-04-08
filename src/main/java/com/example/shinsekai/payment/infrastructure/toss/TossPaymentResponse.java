package com.example.shinsekai.payment.infrastructure.toss;

import com.example.shinsekai.payment.entity.Payment;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public class TossPaymentResponse {
    private String paymentCode;
    private String paymentKey;
    private String memberUuid;
    private String paymentMethod;
    private double paymentPrice;
    private String purchaseName;
    private String status;
    private String receiptUrl;
    private LocalDateTime approvedAt;

    //Card 정보
    private String cardName;
    private String cardNumber;
    private String approveNo;
    private boolean isInterestFree;
    private int installmentPlanMonths;
    private boolean useCardPoint;

    public Payment toEntity(){
        return Payment.builder()
                .paymentCode(paymentCode)
                .paymentKey(paymentKey)
                .memberUuid(memberUuid)
                .paymentMethod(paymentMethod)
                .paymentPrice(paymentPrice)
                .purchaseName(purchaseName)
                .status(status)
                .receiptUrl(receiptUrl)
                .approvedAt(approvedAt)
                .cardName(cardName)
                .cardNumber(cardNumber)
                .approveNo(approveNo)
                .isInterestFree(isInterestFree)
                .installmentPlanMonths(installmentPlanMonths)
                .useCardPoint(useCardPoint)
                .build();
    }
}
