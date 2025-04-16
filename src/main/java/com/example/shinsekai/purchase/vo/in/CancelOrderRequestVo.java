package com.example.shinsekai.purchase.vo.in;

import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class CancelOrderRequestVo {
    private String purchaseCode;
    private String paymentCode;
    private String memberStarbucksCardUuid;
    private String cancelReason;
    private Double refundAmount;
}
