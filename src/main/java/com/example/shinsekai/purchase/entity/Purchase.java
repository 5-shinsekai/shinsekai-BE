package com.example.shinsekai.purchase.entity;

import com.example.shinsekai.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Purchase extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, updatable = false)
    private String purchaseCode;

    @Column(unique = true, nullable = false, updatable = false)
    private String paymentCode;

    @Column( nullable = false, updatable = false)
    private String memberUuid;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PurchaseStatus purchaseStatus;

    @Column(nullable = false)
    private String addressUuid;

    private String cancelReason;

    @Column(updatable = false)
    private String giftCertificateUuid;

    @Column(updatable = false)
    private String couponUuid;

    @Column(updatable = false)
    private Double shipmentFee;

    @Column(nullable = false, updatable = false)
    private Double productTotalPrice;

    public void cancelPurchase(String cancelReason) {
        this.purchaseStatus = PurchaseStatus.CANCELLED;
        this.cancelReason = cancelReason;
    }
}
