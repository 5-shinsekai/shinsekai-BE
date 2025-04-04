package com.example.shinsekai.cart.presentation;

import com.example.shinsekai.cart.application.CartServiceImpl;
import com.example.shinsekai.cart.dto.in.CartCreateRequestDto;
import com.example.shinsekai.cart.vo.in.CartCreateRequestVo;
import com.example.shinsekai.common.entity.BaseResponseEntity;
import com.example.shinsekai.common.entity.BaseResponseStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
