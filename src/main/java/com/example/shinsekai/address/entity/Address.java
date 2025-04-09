package com.example.shinsekai.address.entity;

import com.example.shinsekai.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, updatable = false)
    private String addressUuid;

    @Column(unique = true, nullable = false, updatable = false)
    private String memberUuid;

    @Column(nullable = false, length = 100)
    private String zipNo;
    @Column(nullable = false, length = 30)
    private String addressNickname;
    @Column(nullable = false, length = 100)
    private String deliveryMemo;
    @Column(nullable = false)
    private String totalAddress;
    private boolean isMainAddress;
    @Column(nullable = false, length = 30)
    private String firstPhoneNumber;
    @Column(nullable = false, length = 30)
    private String secondPhoneNumber;
    @Column(nullable = false, length = 30)
    private String receiverName;
    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean isDeleted;
    private LocalDateTime deletedAt;

    @Builder
    public Address(Long id,
                   String addressUuid,
                   String memberUuid,
                   String zipNo,
                   String addressNickname,
                   String deliveryMemo,
                   String totalAddress,
                   boolean isMainAddress,
                   String mainPhone,
                   String subPhone,
                   String receiverName,
                   boolean isDeleted) {
        this.id = id;
        this.addressUuid = addressUuid;
        this.memberUuid = memberUuid;
        this.zipNo = zipNo;
        this.addressNickname = addressNickname;
        this.deliveryMemo = deliveryMemo;
        this.totalAddress = totalAddress;
        this.isMainAddress = isMainAddress;
        this.firstPhoneNumber = mainPhone;
        this.secondPhoneNumber = subPhone;
        this.receiverName = receiverName;
        this.isDeleted = isDeleted;
    }

    public void setDeleted() {
        isDeleted = true;
        deletedAt = LocalDateTime.now();
    }
}
