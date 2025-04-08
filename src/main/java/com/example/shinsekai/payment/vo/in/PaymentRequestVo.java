package com.example.shinsekai.payment.vo.in;

import java.time.LocalDateTime;

public class PaymentRequestVo {
    private String paymentKey;
    private String orderId;
    private String orderName;
    private int totalAmount;
    private String method;
    private String status;
    private LocalDateTime approvedAt;
    private String cardCompany;
    private String cardNumber;
    private String cardApproveNo;
}
