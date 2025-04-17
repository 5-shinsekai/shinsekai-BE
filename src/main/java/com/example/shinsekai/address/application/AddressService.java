package com.example.shinsekai.address.application;

import com.example.shinsekai.address.dto.in.AddressCreateRequestDto;
import com.example.shinsekai.address.dto.in.AddressUpdateRequestDto;
import com.example.shinsekai.address.dto.out.AddressResponseDto;

import java.util.List;

public interface AddressService {

    List<AddressResponseDto> getAddressList(String memberUuid);
    AddressResponseDto getMainAddress(String memberUuid);
    void createAddress(AddressCreateRequestDto addressRequestDto);
    void updateAddress(AddressUpdateRequestDto addressRequestDto);
    void hardDeleteAddress(String addressUuid);
    void softDeleteAddress(String memberUuid, String addressUuid);
}
