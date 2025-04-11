package com.example.shinsekai.cart.application;

import com.example.shinsekai.cart.dto.in.CartCreateRequestDto;
import com.example.shinsekai.cart.dto.in.CartDeleteRequestDto;
import com.example.shinsekai.cart.dto.in.CartUpdateRequestDto;
import com.example.shinsekai.cart.dto.out.CartGroupedByProductTypeDto;

import java.util.List;

public interface CartService {
    CartGroupedByProductTypeDto getAllCarts(String memberUuid); // memberUuid받아오기
    void createCart(CartCreateRequestDto cartCreateRequestDto);
    void updateCart(CartUpdateRequestDto cartUpdateRequestDto);
    void deleteAllCart(String memberUuid);
    void deleteSelectedAllCart (String memberUuid, List<CartDeleteRequestDto> cartDeleRequestDtoList); // deleteAllCartById
    void deleteCart(String memberUuid, CartDeleteRequestDto cartDeleteRequestDto);// 단일 삭제
}
