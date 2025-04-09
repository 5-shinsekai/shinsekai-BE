package com.example.shinsekai.address.infrastructure;

import com.example.shinsekai.address.dto.in.AddressRequestDto;

public interface AddressCustomRepository {
    boolean canAddAddress(String memberUuid, String addressUuid);
    boolean isFirstAddress(String memberUuid);
    void dynamicUpdateAddress(AddressRequestDto addressRequestDto);
}
