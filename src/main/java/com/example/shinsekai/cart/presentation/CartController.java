package com.example.shinsekai.cart.presentation;

import com.example.shinsekai.cart.application.CartServiceImpl;
import com.example.shinsekai.cart.dto.in.CartCheckedUpdateRequestDto;
import com.example.shinsekai.cart.dto.in.CartCreateRequestDto;
import com.example.shinsekai.cart.dto.in.CartDeleteRequestDto;
import com.example.shinsekai.cart.dto.in.CartUpdateRequestDto;
import com.example.shinsekai.cart.vo.in.CartCheckedUpdateRequestVo;
import com.example.shinsekai.cart.vo.in.CartCreateRequestVo;
import com.example.shinsekai.cart.vo.in.CartDeleteRequestVo;
import com.example.shinsekai.cart.vo.in.CartUpdateRequestVo;
import com.example.shinsekai.cart.vo.out.CartGetDetailResponseVo;
import com.example.shinsekai.cart.vo.out.CartGroupedByProductTypeVo;
import com.example.shinsekai.cart.vo.out.CartUuidGroupedByProductTypeVo;
import com.example.shinsekai.common.entity.BaseResponseEntity;
import com.example.shinsekai.common.entity.BaseResponseStatus;
import com.example.shinsekai.common.jwt.JwtTokenProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Cart", description = "장바구니 관련 API")
@RequestMapping("api/v1/cart")
@RequiredArgsConstructor
@RestController
public class CartController {

    private final CartServiceImpl cartService;
    private final JwtTokenProvider jwtTokenProvider;

    @Operation(summary = "장바구니 생성")
    @PostMapping
    public BaseResponseEntity<Void> createCart(@Valid @RequestBody CartCreateRequestVo cartCreateRequestVo) {
        cartService.createCart(CartCreateRequestDto.from(jwtTokenProvider.getMemberUuid(), cartCreateRequestVo));
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }

    @Operation(summary = "장바구니 조회")
    @GetMapping
    public BaseResponseEntity<CartGroupedByProductTypeVo> getAllCarts() {
        return new BaseResponseEntity<>(cartService.getAllCarts(jwtTokenProvider.getMemberUuid())
                .toVo());
    }

    @Operation(summary = "장바구니 uuid 조회")
    @GetMapping("/uuid")
    public BaseResponseEntity<CartUuidGroupedByProductTypeVo> getAllCartsUuid() {
        return new BaseResponseEntity<>(cartService.getAllCartsUuid(jwtTokenProvider.getMemberUuid())
                .toVo());
    }

    @Operation(summary = "장바구니 detail 조회")
    @GetMapping("/{cartUuid}")
    public BaseResponseEntity<CartGetDetailResponseVo> getAllCartsDetail(@PathVariable String cartUuid) {
        return new BaseResponseEntity<>(cartService.getAllCartsDetail(cartUuid)
                .toVo());
    }

    @Operation(summary = "체크된 장바구니 조회")
    @GetMapping("/checked")
    public BaseResponseEntity<CartGroupedByProductTypeVo> getAllCheckedCarts() {
        return new BaseResponseEntity<>(cartService.getAllCheckedCarts(jwtTokenProvider.getMemberUuid())
                .toVo());
    }

    @Operation(summary = "장바구니 수정")
    @PutMapping
    public BaseResponseEntity<Void> updateCart(@Valid @RequestBody CartUpdateRequestVo cartUpdateRequestVo) {
        cartService.updateCart(CartUpdateRequestDto
                .from(jwtTokenProvider.getMemberUuid(), cartUpdateRequestVo));
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }

    @Operation(summary = "장바구니 checked 상태 변경")
    @PutMapping("/checked")
    public BaseResponseEntity<Void> updateCartChecked(
            @RequestBody CartCheckedUpdateRequestVo cartCheckedUpdateRequestVo) {
        cartService.updateAllCartChecked(CartCheckedUpdateRequestDto
                .from(jwtTokenProvider.getMemberUuid(), cartCheckedUpdateRequestVo));
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }


    @Operation(summary = "선택된 장바구니 단일 삭제")
    @DeleteMapping("/{cartUuid}")
    public BaseResponseEntity<Void> deleteCart(@PathVariable String cartUuid) {
        cartService.deleteCart(jwtTokenProvider.getMemberUuid(), CartDeleteRequestDto.from(cartUuid));
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }

    @Operation(summary = "선택된 장바구니 리스트 삭제")
    @DeleteMapping("/list")
    public BaseResponseEntity<Void> deleteSelectedAllCart(
            @RequestBody List<CartDeleteRequestVo> cartDeleteRequestVoList) {
        cartService.deleteSelectedAllCart(jwtTokenProvider.getMemberUuid(),
                cartDeleteRequestVoList.stream().map(CartDeleteRequestDto::from).toList());
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }

    @Operation(summary = "장바구니 전체 삭제")
    @DeleteMapping("/all")
    public BaseResponseEntity<Void> deleteAllCart() {
        cartService.deleteAllCart(jwtTokenProvider.getMemberUuid());
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }
}
