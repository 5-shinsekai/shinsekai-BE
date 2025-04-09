package com.example.shinsekai.cart.presentation;

import com.example.shinsekai.cart.application.CartServiceImpl;
import com.example.shinsekai.cart.dto.in.CartCreateRequestDto;
import com.example.shinsekai.cart.dto.in.CartDeleteRequestDto;
import com.example.shinsekai.cart.vo.in.CartCreateRequestVo;
import com.example.shinsekai.cart.vo.in.CartDeleteRequestVo;
import com.example.shinsekai.cart.vo.out.CartGetResponseVo;
import com.example.shinsekai.cart.vo.out.CartGroupedByProductTypeVo;
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

@Tag(name = "Cart", description = "장바구니 관련 API")
@RequestMapping("api/v1/cart")
@RequiredArgsConstructor
@RestController
public class CartController {

    private final CartServiceImpl cartService;
    private final JwtTokenProvider jwtTokenProvider;

    @Operation(summary = "장바구니 생성")
    @PostMapping
    public BaseResponseEntity<Void> createCart(@Valid @RequestBody CartCreateRequestVo cartCreateRequestVo){
        cartService.createCart(CartCreateRequestDto.from(cartCreateRequestVo));
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }

    @Operation(summary = "장바구니 조회")
    @GetMapping
    public BaseResponseEntity<CartGroupedByProductTypeVo> getAllCarts(HttpServletRequest request){
        return new BaseResponseEntity<>(cartService.getAllCarts(jwtTokenProvider.getAccessToken(request))
                .toVo());
    }

    @Operation(summary = "선택된 장바구니 단일 삭제")
    @DeleteMapping("/{id}")
    public BaseResponseEntity<Void> deleteCart(HttpServletRequest request, @PathVariable Long id){
        cartService.deleteCart(jwtTokenProvider.getAccessToken(request),CartDeleteRequestDto.from(id));
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }

    @Operation(summary = "선택된 장바구니 리스트 삭제")
    @DeleteMapping("/list")
    public BaseResponseEntity<Void> deleteSelectedAllCart(HttpServletRequest request,
                                                          @RequestBody List<CartDeleteRequestVo> cartDeleteRequestVoList){
        cartService.deleteSelectedAllCart(jwtTokenProvider.getAccessToken(request),
                cartDeleteRequestVoList.stream().map(CartDeleteRequestDto::from).toList());
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }

    @Operation(summary = "장바구니 전체 삭제")
    @DeleteMapping("/all")
    public BaseResponseEntity<Void> deleteAllCart(HttpServletRequest request){
        cartService.deleteAllCart(jwtTokenProvider.getAccessToken(request));
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }

//    @Operation(summary = "장바구니 수정")
//    @GetMapping
//    public BaseResponseEntity<> updateCart(HttpServletRequest request){
//
//        return new BaseResponseEntity<>(cartService.getAllCarts(request.getHeader("Authorization").substring(7))
//                .toVo());
//    }
}
