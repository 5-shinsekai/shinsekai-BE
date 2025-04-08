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
    private String receiver;
    private String address;
    private String giftCertificationUuid;
    private String couponUuid;
    private int shipmentFee;
    private double productTotalPrice;

    private List<PurchaseProductRequestVo> orderProductList;
}
