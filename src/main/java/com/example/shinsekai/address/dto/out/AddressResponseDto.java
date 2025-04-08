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
    private String zipCode;
    private String addressNickname;
    private String deriveryMemo;
    private String totalAddress;
    private boolean isMainAddress;
    private String mainPhone;
    private String subPhone;
    private String receiver;
    private boolean isDeleted;

    @Builder
    public AddressResponseDto(Long id,
                              String addressUuid,
                              String memberUuid,
                              String zipCode,
                              String addressNickname,
                              String deriveryMemo,
                              String totalAddress,
                              boolean isMainAddress,
                              String mainPhone,
                              String subPhone,
                              String receiver,
                              boolean isDeleted) {
        this.id = id;
        this.addressUuid = addressUuid;
        this.memberUuid = memberUuid;
        this.zipCode = zipCode;
        this.addressNickname = addressNickname;
        this.deriveryMemo = deriveryMemo;
        this.totalAddress = totalAddress;
        this.isMainAddress = isMainAddress;
        this.mainPhone = mainPhone;
        this.subPhone = subPhone;
        this.receiver = receiver;
        this.isDeleted = isDeleted;
    }

    public static AddressResponseDto from(Address address) {
        return AddressResponseDto.builder()
                .addressUuid(address.getAddressUuid())
                .memberUuid(address.getMemberUuid())
                .zipCode(address.getZipCode())
                .addressNickname(address.getAddressNickname())
                .deriveryMemo(address.getDeriveryMemo())
                .totalAddress(address.getTotalAddress())
                .isMainAddress(address.isMainAddress())
                .mainPhone(address.getMainPhone())
                .subPhone(address.getSubPhone())
                .receiver(address.getReceiver())
                .isDeleted(address.isDeleted())
                .build();
    }

    public AddressResponseVo toVo() {
        return AddressResponseVo.builder()
                .addressUuid(addressUuid)
                .memberUuid(memberUuid)
                .zipCode(zipCode)
                .addressNickname(addressNickname)
                .deriveryMemo(deriveryMemo)
                .totalAddress(totalAddress)
                .isMainAddress(isMainAddress)
                .mainPhone(mainPhone)
                .subPhone(subPhone)
                .receiver(receiver)
                .build();
    }
}
