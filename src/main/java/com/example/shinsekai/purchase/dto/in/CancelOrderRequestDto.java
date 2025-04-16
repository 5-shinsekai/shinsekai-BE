package com.example.shinsekai.purchase.dto.in;

import com.example.shinsekai.purchase.vo.in.CancelOrderRequestVo;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CancelOrderRequestDto {
    private String purchaseCode;
    private String paymentCode;
    private String memberStarbucksCardUuid;
    private String memberUuid;
    private String cancelReason;
    private Double refundAmount;

    public static CancelOrderRequestDto from(CancelOrderRequestVo vo, String memberUuid){
        return CancelOrderRequestDto.builder()
                .purchaseCode(vo.getPurchaseCode())
                .paymentCode(vo.getPaymentCode())
                .memberStarbucksCardUuid(vo.getMemberStarbucksCardUuid())
                .memberUuid(memberUuid)
                .cancelReason(vo.getCancelReason())
                .refundAmount(vo.getRefundAmount())
                .build();
    }
}
