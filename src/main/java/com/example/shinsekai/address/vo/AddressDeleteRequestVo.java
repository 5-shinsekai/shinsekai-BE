package com.example.shinsekai.address.vo;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class AddressDeleteRequestVo {

    @NotBlank(message = "INVALID_ADDRESS_UUID")
    private String addressUuid;
}
