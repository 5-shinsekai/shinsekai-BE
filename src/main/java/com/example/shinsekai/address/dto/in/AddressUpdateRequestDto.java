package com.example.shinsekai.address.dto.in;

import com.example.shinsekai.address.entity.Address;
import com.example.shinsekai.address.vo.AddressCreateRequestVo;
import com.example.shinsekai.address.vo.AddressUpdateRequestVo;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import com.example.shinsekai.common.jwt.JwtTokenProvider;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Getter
@Builder
@ToString
public class AddressUpdateRequestDto {

    private final JwtTokenProvider jwtTokenProvider;

    private String addressUuid;
    private String memberUuid;
    private String zipNo;
    private String addressNickname;
    private String deliveryMemo;
    private String totalAddress;
    private Boolean isMainAddress;
    private String firstPhoneNumber;
    private String secondPhoneNumber;
    private String receiverName;

    public static AddressUpdateRequestDto of(AddressUpdateRequestVo addressRequestVo, String memberUuid) {
        return AddressUpdateRequestDto.builder()
                .memberUuid(memberUuid)
                .addressUuid(addressRequestVo.getAddressUuid())
                .zipNo(addressRequestVo.getZipNo())
                .addressNickname(addressRequestVo.getAddressNickname())
                .deliveryMemo(addressRequestVo.getDeliveryMemo())
                .totalAddress(addressRequestVo.getTotalAddress())
                .isMainAddress(addressRequestVo.getIsMainAddress())
                .firstPhoneNumber(addressRequestVo.getFirstPhoneNumber())
                .secondPhoneNumber(addressRequestVo.getSecondPhoneNumber())
                .receiverName(addressRequestVo.getReceiverName())
                .build();
    }


    public Address toEntity(Address address, AddressUpdateRequestDto addressRequestDto) {
        return Address.builder()
                .id(address.getId())
                .memberUuid(addressRequestDto.getMemberUuid())
                .addressUuid(addressRequestDto.getAddressUuid())
                .zipNo(addressRequestDto.getZipNo())
                .addressNickname(addressRequestDto.getAddressNickname())
                .deliveryMemo(addressRequestDto.getDeliveryMemo())
                .totalAddress(addressRequestDto.getTotalAddress())
                .isMainAddress(addressRequestDto.getIsMainAddress())
                .firstPhoneNumber(addressRequestDto.getFirstPhoneNumber())
                .secondPhoneNumber(addressRequestDto.getSecondPhoneNumber())
                .receiverName(addressRequestDto.getReceiverName())
                .build();
    }

}
