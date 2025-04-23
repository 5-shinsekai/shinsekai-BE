package com.example.shinsekai.cart.application;

import com.example.shinsekai.cart.dto.in.CartCheckedUpdateRequestDto;
import com.example.shinsekai.cart.dto.in.CartCreateRequestDto;
import com.example.shinsekai.cart.dto.in.CartDeleteRequestDto;
import com.example.shinsekai.cart.dto.in.CartUpdateRequestDto;
import com.example.shinsekai.cart.dto.out.CartGetResponseDto;
import com.example.shinsekai.cart.dto.out.CartGroupedByProductTypeDto;
import com.example.shinsekai.cart.dto.out.CartUuidGroupedByProductTypeDto;

import java.util.List;

public interface CartService {
    CartGroupedByProductTypeDto getAllCarts(String memberUuid);
    CartUuidGroupedByProductTypeDto getAllCartsUuid(String memberUuid);
    CartGetResponseDto getAllCartsDetail(String memberUuid);
    void createCart(CartCreateRequestDto cartCreateRequestDto);
    void updateCart(CartUpdateRequestDto cartUpdateRequestDto);
    void deleteAllCart(String memberUuid);
    void deleteSelectedAllCart (String memberUuid, List<CartDeleteRequestDto> cartDeleRequestDtoList); // deleteAllCartById
    void deleteCart(String memberUuid, CartDeleteRequestDto cartDeleteRequestDto);// 단일 삭제
    CartGroupedByProductTypeDto getAllCheckedCarts(String memberUuid);
    void updateAllCartChecked(CartCheckedUpdateRequestDto cartCheckedUpdateRequestDto);
}
