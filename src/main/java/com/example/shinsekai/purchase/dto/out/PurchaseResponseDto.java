package com.example.shinsekai.purchase.dto.out;

import com.example.shinsekai.purchase.entity.Purchase;
import com.example.shinsekai.purchase.entity.PurchaseStatus;
import com.example.shinsekai.purchase.vo.out.PurchaseResponseVo;
import lombok.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PurchaseResponseDto {
    private String purchaseCode;
    private String paymentCode;
    private PurchaseStatus purchaseStatus;
    private String receiver;
    private String address;
    private String giftCertificationUuid;
    private String couponUuid;
    private Double shipmentFee;
    private Double productTotalPrice;

    private List<PurchaseProductListResponseDto> purchaseProductDtoList;

    public PurchaseResponseVo toVo(){
        return PurchaseResponseVo.builder()
                .purchaseCode(purchaseCode)
                .paymentCode(paymentCode)
                .purchaseStatus(purchaseStatus)
                .receiver(receiver)
                .address(address)
                .couponUuid(couponUuid)
                .shipmentFee(shipmentFee)
                .productTotalPrice(productTotalPrice)
                .orderProductList(purchaseProductDtoList.stream().map(PurchaseProductListResponseDto::toVo).toList())
                .build();
    }

    public static PurchaseResponseDto from(Purchase purchase, List<PurchaseProductListResponseDto> purchaseProductDtoList){
        return PurchaseResponseDto.builder()
                .purchaseCode(purchase.getPurchaseCode())
                .paymentCode(purchase.getPaymentCode())
                .purchaseStatus(purchase.getPurchaseStatus())
                .receiver(purchase.getReceiver())
                .address(purchase.getAddress())
                .giftCertificationUuid(purchase.getGiftCertificateUuid())
                .couponUuid(purchase.getCouponUuid())
                .shipmentFee(purchase.getShipmentFee())
                .productTotalPrice(purchase.getProductTotalPrice())
                .purchaseProductDtoList(purchaseProductDtoList)
                .build();
    }
}
