package com.example.shinsekai.address.dto.in;

import com.example.shinsekai.address.entity.Address;
import com.example.shinsekai.address.vo.AddressCreateRequestVo;
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
public class AddressCreateRequestDto {

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

    public static AddressCreateRequestDto of(AddressCreateRequestVo addressRequestVo, String memberUuid) {
        return AddressCreateRequestDto.builder()
                .memberUuid(memberUuid)
                .addressUuid(generateAddressUuid())
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

    public Address toEntity(boolean isMainAddress){
        return Address.builder()
                .addressUuid(addressUuid)
                .memberUuid(memberUuid)
                .zipNo(zipNo)
                .addressNickname(addressNickname)
                .deliveryMemo(deliveryMemo)
                .totalAddress(totalAddress)
                .isMainAddress(isMainAddress)
                .firstPhoneNumber(firstPhoneNumber)
                .secondPhoneNumber(secondPhoneNumber)
                .receiverName(receiverName)
                .build();
    }

    private static String generateAddressUuid() {
        String prefix = "A";
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String uuidPart = UUID.randomUUID().toString().substring(0, 8);

        return prefix + date + "-" + uuidPart;
    }
}
