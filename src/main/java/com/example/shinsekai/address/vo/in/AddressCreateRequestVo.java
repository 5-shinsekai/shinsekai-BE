package com.example.shinsekai.address.vo.in;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class AddressCreateRequestVo {

    @NotBlank(message = "INVALID_ADDRESS_NICKNAME_FORMAT")
    @Size(max = 100, message = "INVALID_ADDRESS_NICKNAME_FORMAT")
    private String addressNickname;

    @NotBlank(message = "INVALID_ZIP_NO_FORMAT")
    @Size(max = 30, message = "INVALID_ZIP_NO_FORMAT")
    @Pattern(regexp = "^\\d+$", message = "INVALID_ZIP_NO_FORMAT")
    private String zipNo;

    @NotBlank(message = "INVALID_ROAD_ADDRESS_FORMAT")
    private String roadAddress;

    @NotBlank(message = "INVALID_DETAIL_ADDRESS_FORMAT")
    private String detailAddress;

    @NotBlank(message = "INVALID_TOTAL_ADDRESS_FORMAT")
    private String totalAddress;

    @NotBlank(message = "INVALID_PHONE_FORMAT")
    @Pattern(regexp = "^\\d{3}-\\d{4}-\\d{4}$", message = "INVALID_PHONE_FORMAT")
    private String firstPhoneNumber;

    @Pattern(regexp = "^\\s*$|^\\d{3}-\\d{4}-\\d{4}$", message = "INVALID_PHONE_FORMAT")
    private String secondPhoneNumber;

    @NotBlank(message = "INVALID_DELIVERY_MEMO_FORMAT")
    @Size(max = 100, message = "INVALID_DELIVERY_MEMO_FORMAT")
    private String deliveryMemo;

    private Boolean isPersonalMemo;

    private Boolean isMainAddress;


    @NotBlank(message = "INVALID_NAME_FORMAT")
    @Pattern(regexp = "^[a-zA-Z가-힣]+(\\s[a-zA-Z가-힣]+)*$", message = "INVALID_NAME_FORMAT")
    @Size(max = 30)
    private String receiverName;
}
