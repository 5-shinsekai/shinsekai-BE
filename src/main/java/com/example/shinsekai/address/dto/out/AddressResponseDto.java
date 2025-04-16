package com.example.shinsekai.address.dto.out;

import com.example.shinsekai.address.entity.Address;
import com.example.shinsekai.address.vo.AddressResponseVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class AddressResponseDto {
    private Long id;
    private String addressUuid;
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

    @Builder
    public AddressResponseDto(Long id,
                              String addressUuid,
                              String addressNickname,
                              String zipNo,
                              String roadAddress,
                              String detailAddress,
                              String totalAddress,
                              String firstPhoneNumber,
                              String secondPhoneNumber,
                              String deliveryMemo,
                              Boolean isPersonalMemo,
                              Boolean isMainAddress,
                              String receiverName) {
        this.id = id;
        this.addressUuid = addressUuid;
        this.addressNickname = addressNickname;
        this.zipNo = zipNo;
        this.roadAddress = roadAddress;
        this.detailAddress = detailAddress;
        this.totalAddress = totalAddress;
        this.firstPhoneNumber = firstPhoneNumber;
        this.secondPhoneNumber = secondPhoneNumber;
        this.deliveryMemo = deliveryMemo;
        this.isPersonalMemo = isPersonalMemo;
        this.isMainAddress = isMainAddress;
        this.receiverName = receiverName;
    }

    public static AddressResponseDto from(Address address) {
        return AddressResponseDto.builder()
                .addressUuid(address.getAddressUuid())
                .addressNickname(address.getAddressNickname())
                .zipNo(address.getZipNo())
                .roadAddress(address.getRoadAddress())
                .detailAddress(address.getDetailedAddress())
                .totalAddress(address.getTotalAddress())
                .firstPhoneNumber(address.getFirstPhoneNumber())
                .secondPhoneNumber(address.getSecondPhoneNumber())
                .deliveryMemo(address.getDeliveryMemo())
                .isPersonalMemo(address.getIsPersonalMemo())
                .isMainAddress(address.getIsMainAddress())
                .receiverName(address.getReceiverName())
                .build();
    }

    public AddressResponseVo toVo() {
        return AddressResponseVo.builder()
                .addressUuid(addressUuid)
                .addressNickname(addressNickname)
                .zipNo(zipNo)
                .roadAddress(roadAddress)
                .detailAddress(detailAddress)
                .totalAddress(totalAddress)
                .firstPhoneNumber(firstPhoneNumber)
                .secondPhoneNumber(secondPhoneNumber)
                .deliveryMemo(deliveryMemo)
                .isPersonalMemo(isPersonalMemo)
                .isMainAddress(isMainAddress)
                .receiverName(receiverName)
                .build();
    }
}
