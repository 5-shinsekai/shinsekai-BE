package com.example.shinsekai.address.application;

import com.example.shinsekai.address.dto.in.AddressRequestDto;
import com.example.shinsekai.address.dto.out.AddressResponseDto;

import java.util.List;

public interface AddressService {

    List<AddressResponseDto> getAddress(String memberUuid);
    void createAddress(AddressRequestDto addressRequestDto);
    void updateAddress(AddressRequestDto addressRequestDto);
    void hardDeleteAddress(String addressUuid);
    void softDeleteAddress(String memberUuid, String addressUuid);
}
