package com.example.shinsekai.address.presentation;

import com.example.shinsekai.address.application.AddressService;
import com.example.shinsekai.address.dto.in.AddressCreateRequestDto;
import com.example.shinsekai.address.dto.in.AddressUpdateRequestDto;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Tag(name = "Address", description = "배송지 관련 API")
@RequestMapping("api/v1/address")
@RequiredArgsConstructor
@RestController
public class AddressController {

    private final AddressService addressService;
    private final JwtTokenProvider jwtTokenProvider;

    @Operation(summary = "배송지 전체 조회")
    @GetMapping("/list")
    public BaseResponseEntity<List<AddressResponseVo>> getAddressList() {
        List<AddressResponseVo> result = addressService.getAddressList(jwtTokenProvider.getMemberUuid())
                .stream()
                .map(AddressResponseDto::toVo)
                .toList();
        return new BaseResponseEntity<>(result);
    }

    @Operation(summary = "기본 배송지 조회")
    @GetMapping("/main")
    public BaseResponseEntity<AddressResponseVo> getMainAddress() {
        AddressResponseVo result = addressService.getMainAddress(jwtTokenProvider.getMemberUuid()).toVo();
        return new BaseResponseEntity<>(result);
    }

    @Operation(summary = "배송지 생성")
    @PostMapping
    public BaseResponseEntity<Void> createAddress(@Valid @RequestBody AddressCreateRequestVo addressRequestVo) {

        addressService.createAddress(AddressCreateRequestDto.of(addressRequestVo, jwtTokenProvider.getMemberUuid()));
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }

    @Operation(summary = "배송지 수정")
    @PutMapping
    public BaseResponseEntity<Void> updateAddress(@Valid @RequestBody AddressUpdateRequestVo addressRequestVo) {
        addressService.updateAddress(AddressUpdateRequestDto.of(addressRequestVo, jwtTokenProvider.getMemberUuid()));
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }
    
    @Operation(summary = "배송지 삭제")
    @DeleteMapping
    public BaseResponseEntity<Void> deleteAddress(@Valid @RequestBody AddressDeleteRequestVo addressRequestVo) {
        addressService.softDeleteAddress(jwtTokenProvider.getMemberUuid(), addressRequestVo.getAddressUuid());
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }
}
