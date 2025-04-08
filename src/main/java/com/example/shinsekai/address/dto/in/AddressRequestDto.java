package com.example.shinsekai.address.dto.in;

import com.example.shinsekai.address.entity.Address;
import com.example.shinsekai.address.vo.AddressRequestVo;
import com.example.shinsekai.common.entity.BaseResponseStatus;
import com.example.shinsekai.common.exception.BaseException;
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
    private String zipCode;
    private String addressNickname;
    private String deriveryMemo;
    private String totalAddress;
    private boolean isMainAddress;
    private String mainPhone;
    private String subPhone;
    private String receiver;
    private boolean isDeleted;

    public static AddressRequestDto from(AddressRequestVo addressRequestVo, String memberUuid) {
        return AddressRequestDto.builder()
                .memberUuid(memberUuid)
                .zipCode(addressRequestVo.getZipCode())
                .addressNickname(addressRequestVo.getAddressNickname())
                .deriveryMemo(addressRequestVo.getDeriveryMemo())
                .totalAddress(addressRequestVo.getTotalAddress())
                .isMainAddress(addressRequestVo.isMainAddress())
                .mainPhone(addressRequestVo.getMainPhone())
                .subPhone(addressRequestVo.getSubPhone())
                .receiver(addressRequestVo.getReceiver())
                .isDeleted(addressRequestVo.isDeleted())
                .build();
    }

    public Address toEntity(boolean isMainAddress){
        return Address.builder()
                .addressUuid(generateAddressUuid())
                .memberUuid(memberUuid)
                .zipCode(zipCode)
                .addressNickname(addressNickname)
                .deriveryMemo(deriveryMemo)
                .totalAddress(totalAddress)
                .isMainAddress(isMainAddress)
                .mainPhone(mainPhone)
                .subPhone(subPhone)
                .receiver(receiver)
                .isDeleted(isDeleted)
                .build();
    }

    private String generateAddressUuid() {
        String prefix = "A";
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String uuidPart = UUID.randomUUID().toString().substring(0, 8);

        return prefix + date + "-" + uuidPart;
    }
}
