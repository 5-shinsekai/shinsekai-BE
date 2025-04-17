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
    private String addressNickname;
    private String zipNo;
    private String roadAddress;
    private String detailAddress;
    private String totalAddress;
    private String firstPhoneNumber;
    private String secondPhoneNumber;
    private String deliveryMemo;
    private Boolean isPersonalMemo;
    private Boolean isMainAddress;
    private String receiverName;

    public static AddressCreateRequestDto of(AddressCreateRequestVo addressRequestVo, String memberUuid) {
        return AddressCreateRequestDto.builder()
                .memberUuid(memberUuid)
                .addressUuid(generateAddressUuid())
                .addressNickname(addressRequestVo.getAddressNickname())
                .zipNo(addressRequestVo.getZipNo())
                .roadAddress(addressRequestVo.getRoadAddress())
                .detailAddress(addressRequestVo.getDetailAddress())
                .totalAddress(addressRequestVo.getTotalAddress())
                .firstPhoneNumber(addressRequestVo.getFirstPhoneNumber())
                .secondPhoneNumber(addressRequestVo.getSecondPhoneNumber())
                .deliveryMemo(addressRequestVo.getDeliveryMemo())
                .isPersonalMemo(addressRequestVo.getIsPersonalMemo())
                .isMainAddress(addressRequestVo.getIsMainAddress())
                .receiverName(addressRequestVo.getReceiverName())
                .build();
    }

    public Address toEntity(boolean isMainAddress){
        return Address.builder()
                .addressUuid(addressUuid)
                .memberUuid(memberUuid)
                .addressNickname(addressNickname)
                .zipNo(zipNo)
                .roadAddress(roadAddress)
                .detailedAddress(detailAddress)
                .totalAddress(totalAddress)
                .firstPhoneNumber(firstPhoneNumber)
                .secondPhoneNumber(secondPhoneNumber)
                .deliveryMemo(deliveryMemo)
                .isPersonalMemo(isPersonalMemo)
                .isMainAddress(isMainAddress)
                .isDeleted(false)
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
