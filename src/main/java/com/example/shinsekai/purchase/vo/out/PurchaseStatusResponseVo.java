package com.example.shinsekai.purchase.vo.out;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class PurchaseStatusResponseVo {
    private Integer paymentCompleted;
    private Integer preparing; // 주문완료
    private Integer shipping;
    private Integer delivered;
    private Integer cancelled;
}
