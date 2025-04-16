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

    @Column(nullable = false, updatable = false)
    private String memberUuid;

    @Column(nullable = false, length = 30)
    private String addressNickname;

    @Column(nullable = false, length = 100)
    private String zipNo;

    @Column(nullable = false, length = 50)
    private String roadAddress;

    @Column(nullable = false, length = 100)
    private String detailedAddress;

    @Column(nullable = false)
    private String totalAddress;

    @Column(nullable = false, length = 30)
    private String firstPhoneNumber;

    @Column(length = 30)
    private String secondPhoneNumber;

    @Column(nullable = false, length = 100)
    private String deliveryMemo;

    private Boolean isPersonalMemo;

    private Boolean isMainAddress;

    @Column(nullable = false, length = 30)
    private String receiverName;

    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE", nullable = false)
    private Boolean isDeleted = false;

    private LocalDateTime deletedAt;

    @Builder
    public Address(Long id,
                   String addressUuid,
                   String memberUuid,
                   String addressNickname,
                   String zipNo,
                   String roadAddress,
                   String detailedAddress,
                   String totalAddress,
                   String firstPhoneNumber,
                   String secondPhoneNumber,
                   String deliveryMemo,
                   Boolean isPersonalMemo,
                   Boolean isMainAddress,
                   String receiverName,
                   Boolean isDeleted,
                   LocalDateTime deletedAt) {
        this.id = id;
        this.addressUuid = addressUuid;
        this.memberUuid = memberUuid;
        this.addressNickname = addressNickname;
        this.zipNo = zipNo;
        this.roadAddress = roadAddress;
        this.detailedAddress = detailedAddress;
        this.totalAddress = totalAddress;
        this.firstPhoneNumber = firstPhoneNumber;
        this.secondPhoneNumber = secondPhoneNumber;
        this.deliveryMemo = deliveryMemo;
        this.isPersonalMemo = isPersonalMemo;
        this.isMainAddress = isMainAddress;
        this.receiverName = receiverName;
        this.isDeleted = isDeleted;
        this.deletedAt = deletedAt;
    }

    public void softDeleted() {
        isDeleted = true;
        deletedAt = LocalDateTime.now();
    }

    public void clearMainAddress() {
        isMainAddress = false;
    }

    public void registerMainAddress() {
        isMainAddress = true;
    }
}
