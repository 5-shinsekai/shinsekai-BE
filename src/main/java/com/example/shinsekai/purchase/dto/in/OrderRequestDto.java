package com.example.shinsekai.purchase.dto.in;

import com.example.shinsekai.payment.entity.PaymentStatus;
import com.example.shinsekai.purchase.entity.PurchaseStatus;
import com.example.shinsekai.purchase.vo.in.OrderRequestVo;
import lombok.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderRequestDto {
    //구매
    private String purchaseCode;
    private String memberUuid;
    private PurchaseStatus purchaseStatus;
    private String addressUuid;
    private String giftCertificationUuid;
    private String couponUuid;
    private Double shipmentFee;
    private Double productTotalPrice;

    //결제
    private String purchaseName;
    private Double paymentPrice;
    private String paymentMethod;
    private PaymentStatus paymentStatus;
    private String receiptUrl;

    //스타벅스 카드
    private String memberStarbucksCardUuid;

    public static OrderRequestDto from(OrderRequestVo vo, String memberUuid){
        return OrderRequestDto.builder()
                .purchaseCode(generateOrderCode())
                .memberUuid(memberUuid)
                .purchaseStatus(vo.getPurchaseStatus())
                .addressUuid(vo.getAddressUuid())
                .giftCertificationUuid(vo.getGiftCertificationUuid())
                .couponUuid(vo.getCouponUuid())
                .shipmentFee(vo.getShipmentFee())
                .productTotalPrice(vo.getProductTotalPrice())

                // 결제
                .purchaseName(vo.getOrderName())
                .paymentPrice(vo.getPaymentPrice())
                .paymentMethod(vo.getPaymentMethod())
                .paymentStatus(vo.getPaymentStatus())
                .receiptUrl(vo.getReceiptUrl())

                // 스타벅스 카드
                .memberStarbucksCardUuid(vo.getMemberStarbucksCardUuid())
                .build();
    }
    public static String generateOrderCode() {
        String prefix = "O";
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String uuidPart = UUID.randomUUID().toString().substring(0, 8);

        return prefix + date + "-" + uuidPart;
    }
}
