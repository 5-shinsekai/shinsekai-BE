package com.example.shinsekai.purchase.dto.in;

import com.example.shinsekai.purchase.entity.Purchase;
import com.example.shinsekai.purchase.entity.PurchaseStatus;
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
    private String addressUuid;
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
                .addressUuid(this.addressUuid)
                .giftCertificateUuid(this.giftCertificationUuid)
                .couponUuid(this.couponUuid)
                .shipmentFee(this.shipmentFee)
                .productTotalPrice(this.productTotalPrice)
                .build();
    }

    public static PurchaseRequestDto fromOrder(OrderRequestDto orderRequestDto, String paymentCode){
        return PurchaseRequestDto.builder()
                .purchaseCode(orderRequestDto.getPurchaseCode())
                .paymentCode(paymentCode)
                .memberUuid(orderRequestDto.getMemberUuid())
                .purchaseStatus(orderRequestDto.getPurchaseStatus())
                .addressUuid(orderRequestDto.getAddressUuid())
                .giftCertificationUuid(orderRequestDto.getGiftCertificationUuid())
                .couponUuid(orderRequestDto.getCouponUuid())
                .shipmentFee(orderRequestDto.getShipmentFee())
                .productTotalPrice(orderRequestDto.getProductTotalPrice())
                .build();
    }
}
