package com.example.shinsekai.cart.application;

import com.example.shinsekai.cart.dto.in.CartCreateRequestDto;
import com.example.shinsekai.cart.dto.in.CartDeleteRequestDto;
import com.example.shinsekai.cart.dto.in.CartUpdateRequestDto;
import com.example.shinsekai.cart.dto.out.CartGroupedByProductTypeDto;

import java.util.List;

public interface CartService {
    CartGroupedByProductTypeDto getAllCarts(String token); // memberUuid받아오기
    void createCart(CartCreateRequestDto cartCreateRequestDto);
    void updateCart(String token, CartUpdateRequestDto cartUpdateRequestDto);
    void deleteCart(String token, List<CartDeleteRequestDto> cartDeleRequestDtoList); // deleteAllCartById
    // 단일 삭제
}
