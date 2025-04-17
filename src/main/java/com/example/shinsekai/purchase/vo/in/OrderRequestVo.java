package com.example.shinsekai.purchase.vo.in;

import com.example.shinsekai.payment.entity.PaymentStatus;
import com.example.shinsekai.purchase.entity.PurchaseStatus;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class OrderRequestVo {
    //구매
    private PurchaseStatus purchaseStatus;
    private String giftCertificationUuid;
    private String couponUuid;
    private Double shipmentFee;
    private Double productTotalPrice;
    private String addressUuid;

    private List<PurchaseProductRequestVo> orderProductList;

    //결제
    private String orderName;
    private Double paymentPrice;
    private String paymentMethod;
    private PaymentStatus paymentStatus;
    private String receiptUrl;

    //스타벅스 카드
    private String memberStarbucksCardUuid;
}
