package com.example.shinsekai.purchase.vo.in;

import com.example.shinsekai.purchase.entity.PurchaseStatus;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@ToString
public class OrderRequestVo {
    //구매
    private String purchaseCode;

    private PurchaseStatus purchaseStatus;
    private String receiver;
    private String address;
    private String giftCertificationUuid;
    private String couponUuid;
    private int shipmentFee;
    private double productTotalPrice;

    private List<PurchaseProductRequestVo> orderProductList;

    //결제
    private String paymentKey;
    private String orderName;
    private Double paymentPrice;
    private String paymentMethod;
    private String status;
    private String receiptUrl;
    private LocalDateTime approvedAt;

    //간편결제
    private String cardName;
    private String cardNumber;
    private String approveNo;
    private boolean isInterestFree;
    private int installmentPlanMonths;
    private boolean useCardPoint;

    //스타벅스 카드
    private String memberStarbucksCardUuid;
}
