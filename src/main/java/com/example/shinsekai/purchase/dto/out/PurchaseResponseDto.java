package com.example.shinsekai.purchase.dto.out;

import com.example.shinsekai.purchase.entity.Purchase;
import com.example.shinsekai.purchase.entity.PurchaseStatus;
import com.example.shinsekai.purchase.vo.out.PurchaseResponseVo;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PurchaseResponseDto {
    private String purchaseCode;
    private String paymentCode;
    private PurchaseStatus purchaseStatus;
    private String addressUuid;
    private String giftCertificationUuid;
    private String couponUuid;
    private Double shipmentFee;
    private Double productTotalPrice;
    private LocalDateTime createdAt;

    private List<PurchaseProductListResponseDto> purchaseProductDtoList;

    public static PurchaseResponseDto from(Purchase purchase, List<PurchaseProductListResponseDto> purchaseProductDtoList) {
        return PurchaseResponseDto.builder()
                .purchaseCode(purchase.getPurchaseCode())
                .paymentCode(purchase.getPaymentCode())
                .purchaseStatus(purchase.getPurchaseStatus())
                .addressUuid(purchase.getAddressUuid())
                .giftCertificationUuid(purchase.getGiftCertificateUuid())
                .couponUuid(purchase.getCouponUuid())
                .shipmentFee(purchase.getShipmentFee())
                .productTotalPrice(purchase.getProductTotalPrice())
                .purchaseProductDtoList(purchaseProductDtoList)
                .createdAt(purchase.getCreatedAt())
                .build();
    }

    public PurchaseResponseVo toVo() {
        return PurchaseResponseVo.builder()
                .purchaseCode(purchaseCode)
                .paymentCode(paymentCode)
                .purchaseStatus(purchaseStatus)
                .addressUuid(addressUuid)
                .couponUuid(couponUuid)
                .shipmentFee(shipmentFee)
                .productTotalPrice(productTotalPrice)
                .createdAt(createdAt)
                .orderProductList(purchaseProductDtoList.stream().map(PurchaseProductListResponseDto::toVo).toList())
                .build();
    }
}
