package com.example.shinsekai.purchase.dto.in;

import com.example.shinsekai.purchase.entity.PurchaseStatus;
import com.example.shinsekai.purchase.vo.in.OrderRequestVo;
import lombok.*;

import java.time.LocalDateTime;
@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderRequestDto {
    //구매
    private String purchaseCode;
    private String memberUuid;
    private PurchaseStatus purchaseStatus;
    private String receiver;
    private String address;
    private String giftCertificationUuid;
    private String couponUuid;
    private int shipmentFee;
    private double productTotalPrice;

    //결제
    private String paymentKey;
    private String purchaseName;
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

    public static OrderRequestDto from(OrderRequestVo vo, String memberUuid){
        return OrderRequestDto.builder()
                .purchaseCode(vo.getPurchaseCode())
                .memberUuid(memberUuid)
                .purchaseStatus(vo.getPurchaseStatus())
                .receiver(vo.getReceiver())
                .address(vo.getAddress())
                .giftCertificationUuid(vo.getGiftCertificationUuid())
                .couponUuid(vo.getCouponUuid())
                .shipmentFee(vo.getShipmentFee())
                .productTotalPrice(vo.getProductTotalPrice())

                // 결제
                .paymentKey(vo.getPaymentKey())
                .purchaseName(vo.getOrderName())
                .paymentPrice(vo.getPaymentPrice())
                .paymentMethod(vo.getPaymentMethod())
                .status(vo.getStatus())
                .receiptUrl(vo.getReceiptUrl())
                .approvedAt(vo.getApprovedAt())

                // 간편결제
                .cardName(vo.getCardName())
                .cardNumber(vo.getCardNumber())
                .approveNo(vo.getApproveNo())
                .isInterestFree(vo.isInterestFree())
                .installmentPlanMonths(vo.getInstallmentPlanMonths())
                .useCardPoint(vo.isUseCardPoint())

                // 스타벅스 카드
                .memberStarbucksCardUuid(vo.getMemberStarbucksCardUuid())
                .build();
    }
}
