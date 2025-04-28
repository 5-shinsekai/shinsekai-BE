package com.example.shinsekai.payment.dto.in;

import com.example.shinsekai.payment.entity.Payment;
import com.example.shinsekai.payment.entity.PaymentStatus;
import com.example.shinsekai.purchase.dto.in.OrderRequestDto;
import lombok.*;

import java.time.LocalDate;
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
    private String purchaseName;
    private Double paymentPrice;
    private String paymentMethod;
    private PaymentStatus status;
    private String receiptUrl;

    //스타벅스 카드
    private String memberStarbucksCardUuid;

    public static PaymentRequestDto fromOrder(OrderRequestDto orderRequestDto) {
        return PaymentRequestDto.builder()
                .memberUuid(orderRequestDto.getMemberUuid())
                .paymentCode(generatePaymentCode())
                .purchaseName(orderRequestDto.getPurchaseName())
                .paymentPrice(orderRequestDto.getPaymentPrice())
                .paymentMethod(orderRequestDto.getPaymentMethod())
                .status(orderRequestDto.getPaymentStatus())
                .receiptUrl(orderRequestDto.getReceiptUrl())
                .memberStarbucksCardUuid(orderRequestDto.getMemberStarbucksCardUuid())
                .build();
    }

    public static String generatePaymentCode() {
        String prefix = "PM";
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String uuidPart = UUID.randomUUID().toString().substring(0, 8);

        return prefix + date + "-" + uuidPart;
    }

    public Payment toEntity() {
        return Payment.builder()
//                .paymentKey(paymentKey)
                .paymentCode(paymentCode)
                .memberUuid(memberUuid)
                .purchaseName(purchaseName)
                .paymentPrice(paymentPrice)
                .paymentMethod(paymentMethod)
                .status(status)
                .receiptUrl(receiptUrl)
                .starbucksCardUuid(memberStarbucksCardUuid)
                .build();
    }
}
