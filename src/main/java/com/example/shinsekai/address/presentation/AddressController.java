package com.example.shinsekai.address.presentation;

import com.example.shinsekai.address.application.AddressService;
import com.example.shinsekai.address.dto.in.AddressRequestDto;
import com.example.shinsekai.address.dto.out.AddressResponseDto;
import com.example.shinsekai.address.vo.AddressDeleteRequestVo;
import com.example.shinsekai.address.vo.AddressCreateRequestVo;
import com.example.shinsekai.address.vo.AddressResponseVo;
import com.example.shinsekai.address.vo.AddressUpdateRequestVo;
import com.example.shinsekai.common.entity.BaseResponseEntity;
import com.example.shinsekai.common.entity.BaseResponseStatus;
import com.example.shinsekai.common.jwt.JwtTokenProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Address", description = "배송지 관련 API")
@RequestMapping("api/v1/address")
@RequiredArgsConstructor
@RestController
public class AddressController {

    private final AddressService addressService;
    private final JwtTokenProvider jwtTokenProvider;

    @Operation(summary = "배송지 조회")
    @GetMapping
    public BaseResponseEntity<List<AddressResponseVo>> getAddressList(HttpServletRequest request) {
        return new BaseResponseEntity<>(
                addressService.getAddress(jwtTokenProvider.getAccessToken(request))
                        .stream()
                        .map(AddressResponseDto::toVo)
                        .toList());
    }

    @Operation(summary = "배송지 생성")
    @PostMapping
    public BaseResponseEntity<Void> createAddress(HttpServletRequest request,
                                                  @Valid @RequestBody AddressCreateRequestVo addressRequestVo) {

        addressService.createAddress(AddressRequestDto.from(addressRequestVo, jwtTokenProvider.getAccessToken(request)));
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }

    @Operation(summary = "배송지 수정")
    @PutMapping
    public BaseResponseEntity<Void> updateAddress(HttpServletRequest request,
                                                  @Valid @RequestBody AddressUpdateRequestVo addressRequestVo) {
        addressService.updateAddress(AddressRequestDto.fromForUpdate(addressRequestVo, jwtTokenProvider.getAccessToken(request)));
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }
    
    @Operation(summary = "배송지 삭제")
    @DeleteMapping
    public BaseResponseEntity<Void> deleteAddress(HttpServletRequest request,
                                                  @Valid @RequestBody AddressDeleteRequestVo addressRequestVo) {
        addressService.softDeleteAddress(jwtTokenProvider.getAccessToken(request), addressRequestVo.getAddressUuid());
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }
}
