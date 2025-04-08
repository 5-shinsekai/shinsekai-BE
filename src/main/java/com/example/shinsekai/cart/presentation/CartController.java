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

    @Operation(summary = "장바구니 생성")
    @PostMapping
    public BaseResponseEntity<Void> createCart(@Valid @RequestBody CartCreateRequestVo cartCreateRequestVo){
        cartService.createCart(CartCreateRequestDto.from(cartCreateRequestVo));
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }

    @Operation(summary = "장바구니 조회")
    @GetMapping
    public BaseResponseEntity<CartGroupedByProductTypeVo> getAllCarts(HttpServletRequest request){
        return new BaseResponseEntity<>(cartService.getAllCarts(request.getHeader("Authorization").substring(7))
                .toVo());
    }

    @Operation(summary = "장바구니 삭제")
    @DeleteMapping
    public BaseResponseEntity<Void> deleteCart(HttpServletRequest request, List<CartDeleteRequestVo> cartDeleteRequestVoList){
        cartService.deleteCart(request.getHeader("Authorization").substring(7),
                cartDeleteRequestVoList.stream().map(CartDeleteRequestDto::from).toList());
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
