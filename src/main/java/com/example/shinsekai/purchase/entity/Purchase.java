package com.example.shinsekai.purchase.entity;

import com.example.shinsekai.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Purchase extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, updatable = false)
    private String purchaseCode;

    @Column( nullable = false, updatable = false)
    private String memberUuid;

    @Column(nullable = false, updatable = false)
    private String payCode;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PurchaseStatus purchaseStatus;

    private String cancelReason;

    @Column(nullable = false, updatable = false)
    private String receiver;

    @Column(nullable = false, updatable = false)
    private String address;

    @Column(updatable = false)
    private String giftCertificateUuid;

    @Column(updatable = false)
    private String couponUuid;

    @Column(updatable = false)
    private double shipmentFee;

    @Column(nullable = false, updatable = false)
    private double productTotalPrice;


    @Builder
    public Purchase(Long id, String purchaseCode, String memberUuid, String payCode,
                    PurchaseStatus purchaseStatus, String cancelReason, String receiver,
                    String address, String giftCertificateUuid, String couponUuid,
                    double shipmentFee, double productTotalPrice) {
        this.id = id;
        this.purchaseCode = purchaseCode;
        this.memberUuid = memberUuid;
        this.payCode = payCode;
        this.purchaseStatus = purchaseStatus;
        this.cancelReason = cancelReason;
        this.receiver = receiver;
        this.address = address;
        this.giftCertificateUuid = giftCertificateUuid;
        this.couponUuid = couponUuid;
        this.shipmentFee = shipmentFee;
        this.productTotalPrice = productTotalPrice;
    }
}
