package com.example.shinsekai.address.entity;

import com.example.shinsekai.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 255, updatable = false)
    private String addressUuid;

    @Column(nullable = false, length = 255, updatable = false)
    private String memberUuid;

    @Column(nullable = false, length = 255)
    private String zipCode;

    @Column(nullable = false, length = 10)
    private String addressNickname;

    @Column(length = 100)
    private String deliveryMemo;

    @Column(nullable = false, length = 150)
    private String totalAddress;

    @Column(nullable = false)
    private boolean isMainAddress;

    @Column(nullable = false, length = 11)
    private String mainPhone;

    @Column(length = 11)
    private String subPhone;

    @Column(nullable = false, length = 10)
    private String receiver;

    private boolean isDeleted;

    @Builder
    public Address(Long id
            , String addressUuid
            , String memberUuid
            , String zipCode
            , String addressNickname
            , String deliveryMemo
            , String totalAddress
            , boolean isMainAddress
            , String mainPhone
            , String subPhone
            , String receiver
            , boolean isDeleted) {
        this.id = id;
        this.addressUuid = addressUuid;
        this.memberUuid = memberUuid;
        this.zipCode = zipCode;
        this.addressNickname = addressNickname;
        this.deliveryMemo = deliveryMemo;
        this.totalAddress = totalAddress;
        this.isMainAddress = isMainAddress;
        this.mainPhone = mainPhone;
        this.subPhone = subPhone;
        this.receiver = receiver;
        this.isDeleted = isDeleted;
    }
}
