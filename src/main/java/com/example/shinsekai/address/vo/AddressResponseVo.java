package com.example.shinsekai.address.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class AddressResponseVo {
    private String addressUuid;
    private String addressNickname;
    private String zipNo;
    private String roadAddr;
    private String detailAddr;
    private String totalAddress;
    private String firstPhoneNumber;
    private String secondPhoneNumber;
    private String deliveryMemo;
    private Boolean isPersonalMemo;
    private Boolean isMainAddress;
    private String receiverName;

    @Builder
    public AddressResponseVo(String addressUuid,
                             String addressNickname,
                             String zipNo,
                             String roadAddr,
                             String detailAddr,
                             String totalAddress,
                             String firstPhoneNumber,
                             String secondPhoneNumber,
                             String deliveryMemo,
                             Boolean isPersonalMemo,
                             Boolean isMainAddress,
                             String receiverName) {
        this.addressUuid = addressUuid;
        this.addressNickname = addressNickname;
        this.zipNo = zipNo;
        this.roadAddr = roadAddr;
        this.detailAddr = detailAddr;
        this.totalAddress = totalAddress;
        this.firstPhoneNumber = firstPhoneNumber;
        this.secondPhoneNumber = secondPhoneNumber;
        this.deliveryMemo = deliveryMemo;
        this.isPersonalMemo = isPersonalMemo;
        this.isMainAddress = isMainAddress;
        this.receiverName = receiverName;
    }

}
