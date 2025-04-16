package com.example.shinsekai.payment.vo.out;

import com.example.shinsekai.payment.entity.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@AllArgsConstructor
public class PaymentResponseVo {
    private String paymentCode;
    private String purchaseName;
    private Double paymentPrice;
    private String paymentMethod;
    private PaymentStatus status;
    private String starbucksCardUuid;
}
