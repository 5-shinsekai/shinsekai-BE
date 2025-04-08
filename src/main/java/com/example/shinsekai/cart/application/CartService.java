package com.example.shinsekai.cart.application;

import com.example.shinsekai.cart.dto.in.CartCreateRequestDto;
import com.example.shinsekai.cart.dto.in.CartDeleteRequestDto;
import com.example.shinsekai.cart.dto.in.CartUpdateRequestDto;
import com.example.shinsekai.cart.dto.out.CartGroupedByProductTypeDto;

import java.util.List;

public interface CartService {
    CartGroupedByProductTypeDto getAllCarts(String token);
    void createCart(CartCreateRequestDto cartCreateRequestDto);
    void updateCart(String token, CartUpdateRequestDto cartUpdateRequestDto);
    void deleteCart(String token, List<CartDeleteRequestDto> cartDeleRequestDtoList);
}
