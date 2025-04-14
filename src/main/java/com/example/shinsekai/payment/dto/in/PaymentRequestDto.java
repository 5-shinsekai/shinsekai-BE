package com.example.shinsekai.payment.dto.in;

import com.example.shinsekai.payment.entity.Payment;
import com.example.shinsekai.payment.vo.in.PaymentRequestVo;
import com.example.shinsekai.purchase.dto.in.OrderRequestDto;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentRequestDto {
    private String memberUuid;
    private String paymentCode;
    private String paymentKey;
    private String purchaseName;
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

    public Payment toEntity(){
        return Payment.builder()
                .paymentKey(paymentKey)
                .paymentCode(paymentCode)
                .memberUuid(memberUuid)
                .purchaseName(purchaseName)
                .paymentPrice(paymentPrice)
                .paymentMethod(paymentMethod)
                .status(status)
                .receiptUrl(receiptUrl)
                .approvedAt(approvedAt)
                .cardName(cardName)
                .cardNumber(cardNumber)
                .approveNo(approveNo)
                .isInterestFree(isInterestFree)
                .installmentPlanMonths(installmentPlanMonths)
                .useCardPoint(useCardPoint)
                .starbucksCardUuid(memberStarbucksCardUuid)
                .build();
    }

    public static PaymentRequestDto from(PaymentRequestVo paymentRequestVo, String memberUuid){
        return PaymentRequestDto.builder()
                .memberUuid(memberUuid)
                .paymentCode(generatePaymentCode())
                .paymentKey(paymentRequestVo.getPaymentKey())
                .purchaseName(paymentRequestVo.getOrderName())
                .paymentPrice(paymentRequestVo.getPaymentPrice())
                .paymentMethod(paymentRequestVo.getPaymentMethod())
                .status(paymentRequestVo.getStatus())
                .receiptUrl(paymentRequestVo.getReceiptUrl())
                .approvedAt(paymentRequestVo.getApprovedAt())
                .cardName(paymentRequestVo.getCardName())
                .cardNumber(paymentRequestVo.getCardNumber())
                .approveNo(paymentRequestVo.getApproveNo())
                .isInterestFree(paymentRequestVo.isInterestFree())
                .installmentPlanMonths(paymentRequestVo.getInstallmentPlanMonths())
                .useCardPoint(paymentRequestVo.isUseCardPoint())
                .memberStarbucksCardUuid(paymentRequestVo.getMemberStarbucksCardUuid())
                .build();
    }

    public static PaymentRequestDto fromOrder(OrderRequestDto orderRequestDto){
        return PaymentRequestDto.builder()
                .memberUuid(orderRequestDto.getMemberUuid())
                .paymentCode(generatePaymentCode())
                .paymentKey(orderRequestDto.getPaymentKey())
                .purchaseName(orderRequestDto.getPurchaseName())
                .paymentPrice(orderRequestDto.getPaymentPrice())
                .paymentMethod(orderRequestDto.getPaymentMethod())
                .status(orderRequestDto.getStatus())
                .receiptUrl(orderRequestDto.getReceiptUrl())
                .approvedAt(orderRequestDto.getApprovedAt())
                .cardName(orderRequestDto.getCardName())
                .cardNumber(orderRequestDto.getCardNumber())
                .approveNo(orderRequestDto.getApproveNo())
                .isInterestFree(orderRequestDto.isInterestFree())
                .installmentPlanMonths(orderRequestDto.getInstallmentPlanMonths())
                .useCardPoint(orderRequestDto.isUseCardPoint())
                .memberStarbucksCardUuid(orderRequestDto.getMemberStarbucksCardUuid())
                .build();
    }

    public static String generatePaymentCode() {
        String prefix = "PM";
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String uuidPart = UUID.randomUUID().toString().substring(0, 8);

        return prefix + date + "-" + uuidPart;
    }
}
