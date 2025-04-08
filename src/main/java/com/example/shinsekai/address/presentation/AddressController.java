package com.example.shinsekai.address.presentation;

import com.example.shinsekai.address.application.AddressService;
import com.example.shinsekai.address.dto.in.AddressRequestDto;
import com.example.shinsekai.address.dto.out.AddressResponseDto;
import com.example.shinsekai.address.vo.AddressRequestVo;
import com.example.shinsekai.address.vo.AddressResponseVo;
import com.example.shinsekai.common.entity.BaseResponseEntity;
import com.example.shinsekai.common.entity.BaseResponseStatus;
import com.example.shinsekai.common.exception.BaseException;
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
                addressService.getAddress(getAccessToken(request))
                        .stream()
                        .map(AddressResponseDto::toVo)
                        .toList());
    }

    @Operation(summary = "배송지 생성")
    @PostMapping
    public BaseResponseEntity<Void> createAddress(HttpServletRequest request,
                                                  @Valid @RequestBody AddressRequestVo addressRequestVo) {

        addressService.createAddress(AddressRequestDto.from(addressRequestVo, getAccessToken(request)));
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }

    @Operation(summary = "배송지 수정")
    @PutMapping
    public BaseResponseEntity<Void> updateAddress(){return null;}
    
    @Operation(summary = "배송지 삭제")
    @DeleteMapping
    public BaseResponseEntity<Void> deleteAddress(){return null;}

    private String getAccessToken(HttpServletRequest request) {
        String accessToken = request.getHeader("Authorization").substring(7);

        String memberUuid;
        // meberUuid 꺼내기 및 검증
        try {
            return memberUuid = jwtTokenProvider.extractAllClaims(accessToken).getSubject();  // extractAllClaims 내부에서 우리가 발급한 토근인지 검증함
        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.TOKEN_NOT_VALID);   // 유효하지 않은 토큰입니다~~ 메세지 날림
        }
    }
}
