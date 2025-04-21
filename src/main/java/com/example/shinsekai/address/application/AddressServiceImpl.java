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
import org.springframework.data.domain.Sort;
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
    public List<AddressResponseDto> getAddressList(String memberUuid) {
        return addressRepository.findActiveMemberByMemberUuid(
                        memberUuid,
                        Sort.by(Sort.Order.desc("isMainAddress"), Sort.Order.desc("createdAt"))
                )
                .stream()
                .map(AddressResponseDto::from)
                .toList();
    }

    @Override
    public AddressResponseDto getMainAddress(String memberUuid) {
        return AddressResponseDto.from(addressRepository.findByMemberUuidAndIsMainAddressIsTrue(memberUuid)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_ADDRESS)
        ));
    }

    @Override
    public AddressResponseDto getAddress(String memberUuid, String addressUuid) {
        return AddressResponseDto.from( addressRepository.findByMemberUuidAndAddressUuid(memberUuid, addressUuid)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_ADDRESS)));
    }

    @Override
    @Transactional
    public void createAddress(AddressCreateRequestDto addressCreateRequestDto) {

        // 최초 배송지인지 검사
        int activeAddressCount = addressRepository.countActiveByMemberUuid(addressCreateRequestDto.getMemberUuid());
        Boolean isMainAddress = false;
        // 최초 등록인 경우
        if (activeAddressCount == 0) {
            isMainAddress = true;
        } 
        // 최초 등록이 아닌 경우
        else if (activeAddressCount > 0 && activeAddressCount < 10) {

            Boolean requestedDtoIsMain =
                    addressCreateRequestDto.getIsMainAddress() == null ? false : addressCreateRequestDto.getIsMainAddress();
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
        Address address = addressRepository
                .findByMemberUuidAndAddressUuid(addressUpdateRequestDto.getMemberUuid()
                        , addressUpdateRequestDto.getAddressUuid())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_ADDRESS));

        Boolean isMainAddressExist =
                addressRepository.findByMemberUuidAndIsMainAddressIsTrue(addressUpdateRequestDto.getMemberUuid())
                        .isPresent();

        Boolean isMainAddress = false;

        // 기본 배송지가 존재한다면
        if (isMainAddressExist) {

            // 요청 들어온 배송지가 메인 주소지라면
            if (addressUpdateRequestDto.getIsMainAddress() != null && addressUpdateRequestDto.getIsMainAddress()) {
                addressRepository.findByMemberUuidAndIsMainAddressIsTrue(addressUpdateRequestDto.getMemberUuid()).get()
                        .clearMainAddress();    // 메인 주소지 여부를 false 처리한다.

                // 저장할 객체를 메인 주소지로 지정
                isMainAddress = true;

            } else {
                isMainAddress = false;
            }

        } 
        // 기본 배송지가 없다면
        else {
            // 저장할 객체를 메인 주소지로 지정
            isMainAddress = true;
        }

        addressRepository.save(addressUpdateRequestDto.toEntity(address.getId(), isMainAddress));

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

        if (address.getIsMainAddress()) {
            throw new BaseException(BaseResponseStatus.FAILED_TO_DELETE_MAIN_ADDRESS);
        }

        log.info("address {}", address.toString());

        address.softDeleted();
    }
}