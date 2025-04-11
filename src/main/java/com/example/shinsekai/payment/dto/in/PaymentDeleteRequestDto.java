package com.example.shinsekai.payment.dto.in;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentDeleteRequestDto {
    private String paymentCode;
    private String memberUuid;

    public static PaymentDeleteRequestDto from(String paymentCode, String memberUuid) {
        return PaymentDeleteRequestDto.builder()
                .paymentCode(paymentCode)
                .memberUuid(memberUuid)
                .build();
    }
}
