package com.example.shinsekai.purchase.vo.in;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CancelOrderRequestVo {
    private String purchaseCode;
    private String paymentCode;
    private String cancelReason;
    private Double totalPrice;
}
