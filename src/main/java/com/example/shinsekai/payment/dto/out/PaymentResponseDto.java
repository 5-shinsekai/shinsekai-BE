package com.example.shinsekai.payment.dto.out;

import com.example.shinsekai.payment.entity.Payment;
import com.example.shinsekai.payment.entity.PaymentStatus;
import com.example.shinsekai.payment.vo.out.PaymentResponseVo;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentResponseDto {
    private String paymentCode;
    private String paymentMethod;
    private Double paymentPrice;
    private String purchaseName;
    private PaymentStatus status;
    private String starbucksCardUuid;

    public PaymentResponseVo toVo(){
        return PaymentResponseVo.builder()
                .paymentCode(paymentCode)
                .paymentMethod(paymentMethod)
                .paymentPrice(paymentPrice)
                .purchaseName(purchaseName)
                .status(status)
                .starbucksCardUuid(starbucksCardUuid)
                .build();
    }

    public static PaymentResponseDto from(Payment payment){
        return PaymentResponseDto.builder()
                .paymentCode(payment.getPaymentCode())
                .paymentMethod(payment.getPaymentMethod())
                .paymentPrice(payment.getPaymentPrice())
                .purchaseName(payment.getPurchaseName())
                .status(payment.getStatus())
                .starbucksCardUuid(payment.getStarbucksCardUuid())
                .build();
    }
}
