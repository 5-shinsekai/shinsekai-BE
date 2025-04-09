package com.example.shinsekai.address.application;

import com.example.shinsekai.address.dto.in.AddressRequestDto;
import com.example.shinsekai.address.dto.out.AddressResponseDto;
import com.example.shinsekai.address.entity.Address;
import com.example.shinsekai.address.infrastructure.AddressCustomRepoImpl;
import com.example.shinsekai.address.infrastructure.AddressRepository;
import com.example.shinsekai.common.entity.BaseResponseStatus;
import com.example.shinsekai.common.exception.BaseException;
import jakarta.transaction.Transactional;
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
    private final AddressCustomRepoImpl addressCustomRepository;

    @Override
    public List<AddressResponseDto> getAddress(String memberUuid) {
        return addressRepository.findByMemberUuid(memberUuid)
                .stream()
                .filter(address -> !address.isDeleted())
                .map(AddressResponseDto::from)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void createAddress(AddressRequestDto addressRequestDto) {

        // 배송지 중복 검사
        if (!addressCustomRepository.canAddAddress(addressRequestDto.getMemberUuid()
                , addressRequestDto.getAddressUuid())) {
            throw new BaseException(BaseResponseStatus.DUPLICATED_ADDRESS);
        }

        // 최초 배송지인지 검사
        boolean isFirstAddress = addressCustomRepository.isFirstAddress(addressRequestDto.getMemberUuid());

        // 최초 배송지인지를 전달하여 isMainAddress 셋팅
        addressRepository.save(addressRequestDto.toEntity(isFirstAddress));
    }

    @Override
    @Transactional
    public void updateAddress(AddressRequestDto addressRequestDto) {
        try {
            addressCustomRepository.dynamicUpdateAddress(addressRequestDto);
        } catch (Exception e) {
            e.printStackTrace();
            throw new  BaseException(BaseResponseStatus.FAILED_TO_SAVE_ADDRESS);
        }
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

        log.info("address {}", address.toString());

        address.setDeleted();
    }
}
