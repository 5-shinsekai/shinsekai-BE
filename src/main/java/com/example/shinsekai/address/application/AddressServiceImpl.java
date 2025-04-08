package com.example.shinsekai.address.application;

import com.example.shinsekai.address.dto.in.AddressRequestDto;
import com.example.shinsekai.address.dto.out.AddressResponseDto;
import com.example.shinsekai.address.entity.Address;
import com.example.shinsekai.address.infrastructure.AddressRepository;
import com.example.shinsekai.common.entity.BaseResponseStatus;
import com.example.shinsekai.common.exception.BaseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
                .filter(address -> !address.isDeleted())
                .map(AddressResponseDto::from)
                .collect(Collectors.toList());
    }

    @Override
    public void createAddress(AddressRequestDto addressRequestDto) {

        // 배송지 중복 검사
        addressRepository.findByMemberUuidAndAddressUuid(addressRequestDto.getMemberUuid(),
                        addressRequestDto.getAddressUuid())
                        .filter(a -> !a.isDeleted())
                        .ifPresent(address -> {
                            throw new BaseException(BaseResponseStatus.DUPLICATED_ADDRESS);
                        });

        // 최초 입력이면 mainAddress -> true
        List<Address> addressList = addressRepository.findByMemberUuid(addressRequestDto.getMemberUuid())
                .stream()
                .filter(address -> !address.isDeleted())
                .toList();
        addressRepository.save(addressRequestDto.toEntity(addressList.isEmpty()));
    }

    @Override
    public void saveAddress(AddressRequestDto addressRequestDto) {

    }

    @Override
    public void deleteAddress(String addressUuid) {

    }
}
