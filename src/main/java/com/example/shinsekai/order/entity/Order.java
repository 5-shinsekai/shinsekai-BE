package com.example.shinsekai.order.entity;

import com.example.shinsekai.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

//@Entity
//@Getter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(unique = true, nullable = false, updatable = false)
//    private String orderCode;
//
//    @Column( nullable = false, updatable = false)
//    private String memberUuid;
//
//    @Column(nullable = false, updatable = false)
//    private String payCode;
//
//    @Enumerated(EnumType.STRING)
//    @Column(nullable = false)
//    private OrderStatus orderStatus;
//
//    private String cancelReason;
//
//    @Column(nullable = false, updatable = false)
//    private String receiver;
//
//    @Column(nullable = false, updatable = false)
//    private String address;
//
//    @Column(updatable = false)
//    private String giftCertificateUuid;
//
//    @Column(updatable = false)
//    private String couponUuid;
//
//    @Column(updatable = false)
//    private double shipmentFee;
//
//    @Column(nullable = false, updatable = false)
//    private double productTotalPrice;
//
//
//    @Builder
//    public Order(Long id, String orderCode, String memberUuid, String payCode,
//                 OrderStatus orderStatus, String cancelReason, String receiver,
//                 String address, String giftCertificateUuid, String couponUuid,
//                 double shipmentFee, double productTotalPrice) {
//        this.id = id;
//        this.orderCode = orderCode;
//        this.memberUuid = memberUuid;
//        this.payCode = payCode;
//        this.orderStatus = orderStatus;
//        this.cancelReason = cancelReason;
//        this.receiver = receiver;
//        this.address = address;
//        this.giftCertificateUuid = giftCertificateUuid;
//        this.couponUuid = couponUuid;
//        this.shipmentFee = shipmentFee;
//        this.productTotalPrice = productTotalPrice;
//    }
}
