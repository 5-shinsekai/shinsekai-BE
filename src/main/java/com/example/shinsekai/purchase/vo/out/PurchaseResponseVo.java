package com.example.shinsekai.purchase.vo.out;

import com.example.shinsekai.purchase.entity.PurchaseStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@ToString
public class PurchaseResponseVo {
    private String purchaseCode;
    private String paymentCode;
    private PurchaseStatus purchaseStatus;
    private String addressUuid;
    private String giftCertificationUuid;
    private String couponUuid;
    private Double shipmentFee;
    private Double productTotalPrice;
    private LocalDateTime createdAt;

    private List<PurchaseProductResponseVo> orderProductList;
}
