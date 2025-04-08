package com.example.shinsekai.address.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class AddressResponseVo {
    private String addressUuid;
    private String memberUuid;
    private String zipCode;
    private String addressNickname;
    private String deriveryMemo;
    private String totalAddress;
    private boolean isMainAddress;
    private String mainPhone;
    private String subPhone;
    private String receiver;
    private boolean isDeleted;

    @Builder
    public AddressResponseVo(String addressUuid,
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
