package com.example.shinsekai.address.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class AddressResponseVo {
    private String addressUuid;
    private String memberUuid;
    private String zipNo;
    private String addressNickname;
    private String deliveryMemo;
    private String totalAddress;
    private boolean isMainAddress;
    private String firstPhoneNumber;
    private String secondPhoneNumber;
    private String receiverName;
    private boolean isDeleted;

    @Builder
    public AddressResponseVo(String addressUuid,
                             String memberUuid,
                             String zipNo,
                             String addressNickname,
                             String deliveryMemo,
                             String totalAddress,
                             boolean isMainAddress,
                             String firstPhoneNumber,
                             String secondPhoneNumber,
                             String receiverName,
                             boolean isDeleted) {
        this.addressUuid = addressUuid;
        this.memberUuid = memberUuid;
        this.zipNo = zipNo;
        this.addressNickname = addressNickname;
        this.deliveryMemo = deliveryMemo;
        this.totalAddress = totalAddress;
        this.isMainAddress = isMainAddress;
        this.firstPhoneNumber = firstPhoneNumber;
        this.secondPhoneNumber = secondPhoneNumber;
        this.receiverName = receiverName;
        this.isDeleted = isDeleted;
    }
}
