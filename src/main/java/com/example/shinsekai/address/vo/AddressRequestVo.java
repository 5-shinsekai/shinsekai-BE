package com.example.shinsekai.address.vo;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class AddressRequestVo {
    private String zipCode;
    private String addressNickname;
    private String deriveryMemo;
    private String totalAddress;
    private boolean isMainAddress;
    private String mainPhone;
    private String subPhone;
    private String receiver;
    private boolean isDeleted;
}
