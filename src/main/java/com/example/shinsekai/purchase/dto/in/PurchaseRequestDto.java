package com.example.shinsekai.purchase.dto.in;

import com.example.shinsekai.purchase.entity.Purchase;
import com.example.shinsekai.purchase.entity.PurchaseStatus;
import com.example.shinsekai.purchase.vo.in.PurchaseRequestVo;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

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
    private int shipmentFee;
    private double productTotalPrice;

    public Purchase toEntity(String memberUuid){
        return Purchase.builder()
                .purchaseCode(this.purchaseCode)
                .paymentCode(this.paymentCode)
                .memberUuid(memberUuid)
                .purchaseStatus(this.purchaseStatus)
                .receiver(this.receiver)
                .address(this.address)
                .giftCertificateUuid(this.giftCertificationUuid)
                .couponUuid(this.couponUuid)
                .shipmentFee(this.shipmentFee)
                .productTotalPrice(this.productTotalPrice)
                .build();
    }

    public static PurchaseRequestDto from(PurchaseRequestVo purchaseRequestVo){
        return PurchaseRequestDto.builder()
                .purchaseCode(purchaseRequestVo.getPurchaseCode())
                .paymentCode(purchaseRequestVo.getPaymentCode())
                .memberUuid(purchaseRequestVo.getMemberUuid())
                .purchaseStatus(purchaseRequestVo.getPurchaseStatus())
                .receiver(purchaseRequestVo.getReceiver())
                .address(purchaseRequestVo.getAddress())
                .giftCertificationUuid(purchaseRequestVo.getGiftCertificationUuid())
                .couponUuid(purchaseRequestVo.getCouponUuid())
                .shipmentFee(purchaseRequestVo.getShipmentFee())
                .productTotalPrice(purchaseRequestVo.getProductTotalPrice())
                .build();
    }

}
