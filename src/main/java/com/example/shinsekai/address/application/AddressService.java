package com.example.shinsekai.address.application;

import com.example.shinsekai.address.dto.in.AddressRequestDto;
import com.example.shinsekai.address.dto.out.AddressResponseDto;

import java.util.List;

public interface AddressService {

    List<AddressResponseDto> getAddress(String memberUuid);
    void createAddress(AddressRequestDto addressRequestDto);
    void saveAddress(AddressRequestDto addressRequestDto);
    void deleteAddress(String addressUuid);
}
