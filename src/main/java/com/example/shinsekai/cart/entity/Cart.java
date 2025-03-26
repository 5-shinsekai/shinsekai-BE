package com.example.shinsekai.cart.entity;

import com.example.shinsekai.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Entity
@NoArgsConstructor
public class Cart extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartPk;

    @Column(nullable = false, length = 255)
    private String memberUuid;

    @Column(nullable = false)
    private Long productOptionkey;

    @Column(nullable = false, length = 255)
    private String productCode;

    @Column(nullable = false)
    private int count;

    @Column(nullable = false, length = 255)
    private boolean checked;

    @Column(nullable = false, length = 255)
    private String symbolMessage;

    @Column(nullable = false)
    private boolean freezedChecked;

    @Column(nullable = false, length = 255)
    private LocalDateTime storageLimitDate;
}