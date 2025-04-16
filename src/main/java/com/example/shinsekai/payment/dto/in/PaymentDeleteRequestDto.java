package com.example.shinsekai.payment.dto.in;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentDeleteRequestDto {
    private String paymentCode;
    private Double refundAmount;
    private String memberUuid;

    public static PaymentDeleteRequestDto from(String paymentCode, Double refundAmount, String memberUuid) {
        return PaymentDeleteRequestDto.builder()
                .paymentCode(paymentCode)
                .refundAmount(refundAmount)
                .memberUuid(memberUuid)
                .build();
    }
}
