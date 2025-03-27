package com.example.shinsekai.cart.presentation;

import com.example.shinsekai.cart.application.CartServiceImp;
import com.example.shinsekai.cart.dto.in.CartCreateRequestDto;
import com.example.shinsekai.cart.vo.in.CartCreateRequestVo;
import com.example.shinsekai.common.entity.BaseResponseEntity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/cart")
public class CartController {

    private final CartServiceImp cartService;

    @PostMapping
    public BaseResponseEntity<Void> createCart(@Valid @RequestBody CartCreateRequestVo cartCreateRequestVo){
        cartService.createCart(CartCreateRequestDto.from(cartCreateRequestVo));
        return new BaseResponseEntity<>();
    }
}
