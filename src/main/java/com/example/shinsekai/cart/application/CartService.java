package com.example.shinsekai.cart.application;

import com.example.shinsekai.cart.dto.in.CartCreateRequestDto;

public interface CartService {
    void createCart(CartCreateRequestDto cartCreateRequestDto);
}
