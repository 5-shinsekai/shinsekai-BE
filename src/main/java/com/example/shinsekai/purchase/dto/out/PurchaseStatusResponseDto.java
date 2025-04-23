package com.example.shinsekai.purchase.dto.out;

import com.example.shinsekai.purchase.vo.out.PurchaseStatusResponseVo;
import lombok.*;

@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseStatusResponseDto {
    private Integer paymentCompleted;
    private Integer preparing; // 주문완료
    private Integer shipping;
    private Integer delivered;
    private Integer cancelled;

    public PurchaseStatusResponseVo toVo(){
        return PurchaseStatusResponseVo.builder()
                .paymentCompleted(this.paymentCompleted)
                .preparing(this.preparing)
                .shipping(this.shipping)
                .delivered(this.delivered)
                .cancelled(this.cancelled)
                .build();
    }
}
