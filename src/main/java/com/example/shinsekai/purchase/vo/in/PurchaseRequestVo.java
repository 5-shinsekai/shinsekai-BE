package com.example.shinsekai.purchase.vo.in;

import com.example.shinsekai.common.entity.BaseEntity;
import com.example.shinsekai.purchase.entity.PurchaseStatus;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class PurchaseRequestVo extends BaseEntity {
    private String purchaseCode;
    private String paymentCode;
    private String memberUuid;
    private PurchaseStatus purchaseStatus;
    private String addressUuid;
    private String giftCertificationUuid;
    private String couponUuid;
    private Double shipmentFee;
    private Double productTotalPrice;

    private List<PurchaseProductRequestVo> orderProductList;
}
