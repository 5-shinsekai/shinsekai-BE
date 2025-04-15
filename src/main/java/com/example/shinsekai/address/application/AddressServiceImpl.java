package com.example.shinsekai.address.application;

import com.example.shinsekai.address.dto.in.AddressCreateRequestDto;
import com.example.shinsekai.address.dto.in.AddressUpdateRequestDto;
import com.example.shinsekai.address.dto.out.AddressResponseDto;
import com.example.shinsekai.address.entity.Address;
import com.example.shinsekai.address.infrastructure.AddressRepository;
import com.example.shinsekai.common.entity.BaseResponseStatus;
import com.example.shinsekai.common.exception.BaseException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.endpoints.internal.Value;

import java.util.Comparator;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService{

    private final AddressRepository addressRepository;

    @Override
    public List<AddressResponseDto> getAddress(String memberUuid) {
        return addressRepository.findByMemberUuid(memberUuid)
                .stream()
                .filter(address -> !address.getIsDeleted())
                .sorted(
                        Comparator.comparing(Address::getIsMainAddress).reversed()
                                .thenComparing(Address::getId) //오름차순     //.reversed(): 내림차순
                )
                .map(AddressResponseDto::from)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void createAddress(AddressCreateRequestDto addressCreateRequestDto) {

        // 최초 배송지인지 검사
        int addressCount = addressRepository.countByMemberUuid(addressCreateRequestDto.getMemberUuid());
        boolean isMainAddress = false;
        // 최초 등록인 경우
        if (addressCount == 0) {
            isMainAddress = true;
        } 
        // 최초 등록이 아닌 경우
        else if (addressCount > 0 && addressCount < 10) {

            Boolean requestedDtoIsMain = addressCreateRequestDto.getIsMainAddress() == null ? false : true;
            if (requestedDtoIsMain) {
                Address prevMainAddress =
                        addressRepository.findByMemberUuidAndIsMainAddressIsTrue(addressCreateRequestDto.getMemberUuid())
                        .orElseThrow(() -> new BaseException(BaseResponseStatus.FAILED_TO_SAVE_ADDRESS));

                prevMainAddress.clearMainAddress();                 // 기존 주소의 메인주소지 해제
                isMainAddress = true;
            }
        }
        // 10개까지만 저장가능  // addressCount >= 10
        else {
            throw new BaseException(BaseResponseStatus.ADDRESS_QUANTITY_LIMIT_EXCEEDED);
        }

        // 최초 배송지인지를 전달하여 isMainAddress 셋팅
        addressRepository.save(addressCreateRequestDto.toEntity(isMainAddress));
    }

    @Override
    @Transactional
    public void updateAddress(AddressUpdateRequestDto addressUpdateRequestDto) {
        addressRepository
                .findByMemberUuidAndAddressUuid(addressUpdateRequestDto.getMemberUuid()
                        , addressUpdateRequestDto.getAddressUuid())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_ADDRESS));

        // 메인 주소지로 저장한다면
        if (addressUpdateRequestDto.getIsMainAddress() != null && addressUpdateRequestDto.getIsMainAddress()) {
            Address prevMainAddress =
                    addressRepository.findByMemberUuidAndIsMainAddressIsTrue(addressUpdateRequestDto.getMemberUuid())
                            .orElseThrow(() -> new BaseException(BaseResponseStatus.FAILED_TO_SAVE_ADDRESS));

            prevMainAddress.clearMainAddress();                 // 기존 주소의 메인주소지 해제
        }

        addressRepository.save(addressUpdateRequestDto.toEntity());
    }

    @Override
    @Transactional
    public void hardDeleteAddress(String addressUuid) {
        Address address = addressRepository.findByAddressUuid(addressUuid)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_ADDRESS));
        addressRepository.delete(address);
    }

    @Override
    @Transactional
    public void softDeleteAddress(String memberUuid, String addressUuid) {
        Address address = addressRepository.findByMemberUuidAndAddressUuid(memberUuid, addressUuid)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_ADDRESS));

        if (address.getIsMainAddress() == true) {
            throw new BaseException(BaseResponseStatus.FAILED_TO_DELETE_MAIN_ADDRESS);
        }

        log.info("address {}", address.toString());

        address.softDeleted();
    }
}