package com.example.shinsekai.purchase.dto.in;

import com.example.shinsekai.purchase.entity.Purchase;
import com.example.shinsekai.purchase.entity.PurchaseStatus;
import com.example.shinsekai.purchase.vo.in.PurchaseRequestVo;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Getter
@ToString
@Builder
public class PurchaseRequestDto {
    private String purchaseCode;
    private String paymentCode;
    private String memberUuid;
    private PurchaseStatus purchaseStatus;
    private String receiver;
    private String address;
    private String giftCertificationUuid;
    private String couponUuid;
    private Double shipmentFee;
    private Double productTotalPrice;

    public Purchase toEntity(){
        return Purchase.builder()
                .purchaseCode(this.purchaseCode)
                .paymentCode(this.paymentCode)
                .memberUuid(this.memberUuid)
                .purchaseStatus(this.purchaseStatus)
                .receiver(this.receiver)
                .address(this.address)
                .giftCertificateUuid(this.giftCertificationUuid)
                .couponUuid(this.couponUuid)
                .shipmentFee(this.shipmentFee)
                .productTotalPrice(this.productTotalPrice)
                .build();
    }

//    public static PurchaseRequestDto from(PurchaseRequestVo purchaseRequestVo, String memberUuid){
//        return PurchaseRequestDto.builder()
//                .purchaseCode(purchaseRequestVo.getPurchaseCode())
//                .paymentCode(purchaseRequestVo.getPaymentCode())
//                .memberUuid(memberUuid)
//                .purchaseStatus(purchaseRequestVo.getPurchaseStatus())
//                .receiver(purchaseRequestVo.getReceiver())
//                .address(purchaseRequestVo.getAddress())
//                .giftCertificationUuid(purchaseRequestVo.getGiftCertificationUuid())
//                .couponUuid(purchaseRequestVo.getCouponUuid())
//                .shipmentFee(purchaseRequestVo.getShipmentFee())
//                .productTotalPrice(purchaseRequestVo.getProductTotalPrice())
//                .build();
//    }

    public static PurchaseRequestDto fromOrder(OrderRequestDto orderRequestDto, String paymentCode){
        return PurchaseRequestDto.builder()
                .purchaseCode(orderRequestDto.getPurchaseCode())
                .paymentCode(paymentCode)
                .memberUuid(orderRequestDto.getMemberUuid())
                .purchaseStatus(orderRequestDto.getPurchaseStatus())
                .receiver(orderRequestDto.getReceiver())
                .address(orderRequestDto.getAddress())
                .giftCertificationUuid(orderRequestDto.getGiftCertificationUuid())
                .couponUuid(orderRequestDto.getCouponUuid())
                .shipmentFee(orderRequestDto.getShipmentFee())
                .productTotalPrice(orderRequestDto.getProductTotalPrice())
                .build();
    }

//    public static String generateOrderCode() {
//        String prefix = "O";
//        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
//        String uuidPart = UUID.randomUUID().toString().substring(0, 8);
//
//        return prefix + date + "-" + uuidPart;
//    }
}
