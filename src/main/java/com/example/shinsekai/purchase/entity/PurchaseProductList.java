package com.example.shinsekai.purchase.entity;

import com.example.shinsekai.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Entity
public class PurchaseProductList extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(updatable = false, nullable = false)
    private Long productOptionId;

    @Column(updatable = false, nullable = false)
    private String productCode;

    @Column(updatable = false, nullable = false)
    private String purchaseCode;

    @Column(updatable = false, nullable = false)
    private double productPrice;

    @Column(updatable = false, nullable = false)
    private String productName;

    @Column(updatable = false, nullable = false)
    private int quantity;

    @Column(updatable = false, nullable = false)
    private String productImageUrl;

    @Column(updatable = false, nullable = false)
    private String productImageDescription;
}