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
    public AddressResponseDto(Long id,
                              String addressUuid,
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
        this.id = id;
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

    public static AddressResponseDto from(Address address) {
        return AddressResponseDto.builder()
                .addressUuid(address.getAddressUuid())
                .memberUuid(address.getMemberUuid())
                .zipNo(address.getZipNo())
                .addressNickname(address.getAddressNickname())
                .deliveryMemo(address.getDeliveryMemo())
                .totalAddress(address.getTotalAddress())
                .isMainAddress(address.isMainAddress())
                .firstPhoneNumber(address.getFirstPhoneNumber())
                .secondPhoneNumber(address.getSecondPhoneNumber())
                .receiverName(address.getReceiverName())
                .isDeleted(address.isDeleted())
                .build();
    }

    public AddressResponseVo toVo() {
        return AddressResponseVo.builder()
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
}
