package com.example.shinsekai.address.entity;

import com.example.shinsekai.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private String zipCode;
    @Column(nullable = false, length = 30)
    private String addressNickname;
    @Column(nullable = false, length = 100)
    private String deriveryMemo;
    @Column(nullable = false)
    private String totalAddress;
    private boolean isMainAddress;
    @Column(nullable = false, length = 30)
    private String mainPhone;
    @Column(nullable = false, length = 30)
    private String subPhone;
    @Column(nullable = false, length = 30)
    private String receiver;
    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean isDeleted;

    @Builder
    public Address(Long id,
                   String addressUuid,
                   String memberUuid,
                   String zipCode,
                   String addressNickname,
                   String deriveryMemo,
                   String totalAddress,
                   boolean isMainAddress,
                   String mainPhone,
                   String subPhone,
                   String receiver,
                   boolean isDeleted) {
        this.id = id;
        this.addressUuid = addressUuid;
        this.memberUuid = memberUuid;
        this.zipCode = zipCode;
        this.addressNickname = addressNickname;
        this.deriveryMemo = deriveryMemo;
        this.totalAddress = totalAddress;
        this.isMainAddress = isMainAddress;
        this.mainPhone = mainPhone;
        this.subPhone = subPhone;
        this.receiver = receiver;
        this.isDeleted = isDeleted;
    }

}
