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
public class AddressRequestDto {

    private final JwtTokenProvider jwtTokenProvider;

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

    public static AddressRequestDto from(AddressCreateRequestVo addressRequestVo, String memberUuid) {
        return AddressRequestDto.builder()
                .memberUuid(memberUuid)
                .addressUuid(generateAddressUuid())
                .zipNo(addressRequestVo.getZipNo())
                .addressNickname(addressRequestVo.getAddressNickname())
                .deliveryMemo(addressRequestVo.getDeliveryMemo())
                .totalAddress(addressRequestVo.getTotalAddress())
                .isMainAddress(addressRequestVo.isMainAddress())
                .firstPhoneNumber(addressRequestVo.getFirstPhoneNumber())
                .secondPhoneNumber(addressRequestVo.getSecondPhoneNumber())
                .receiverName(addressRequestVo.getReceiverName())
                .isDeleted(addressRequestVo.isDeleted())
                .build();
    }

    public static AddressRequestDto fromForUpdate(AddressUpdateRequestVo addressRequestVo, String memberUuid) {
        return AddressRequestDto.builder()
                .memberUuid(memberUuid)
                .addressUuid(addressRequestVo.getAddressUuid())
                .zipNo(addressRequestVo.getZipNo())
                .addressNickname(addressRequestVo.getAddressNickname())
                .deliveryMemo(addressRequestVo.getDeliveryMemo())
                .totalAddress(addressRequestVo.getTotalAddress())
                .isMainAddress(addressRequestVo.isMainAddress())
                .firstPhoneNumber(addressRequestVo.getFirstPhoneNumber())
                .secondPhoneNumber(addressRequestVo.getSecondPhoneNumber())
                .receiverName(addressRequestVo.getReceiverName())
                .isDeleted(addressRequestVo.isDeleted())
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
                .mainPhone(firstPhoneNumber)
                .subPhone(secondPhoneNumber)
                .receiverName(receiverName)
                .isDeleted(isDeleted)
                .build();
    }

    private static String generateAddressUuid() {
        String prefix = "A";
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String uuidPart = UUID.randomUUID().toString().substring(0, 8);

        return prefix + date + "-" + uuidPart;
    }
}
