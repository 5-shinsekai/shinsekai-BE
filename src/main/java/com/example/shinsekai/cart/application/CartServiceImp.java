package com.example.shinsekai.cart.application;

import com.example.shinsekai.cart.dto.in.CartCreateRequestDto;
import com.example.shinsekai.cart.infrastructure.CartRepository;
import com.example.shinsekai.common.entity.BaseResponseStatus;
import com.example.shinsekai.common.exception.BaseException;
import com.example.shinsekai.product.infrastructure.ProductOptionListRepository;
import com.example.shinsekai.product.infrastructure.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartServiceImp implements CartService{

    private final CartRepository cartRepository;
    private final ProductOptionListRepository productOptionListRepository;
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public void createCart(CartCreateRequestDto cartCreateRequestDto) {
        if (!productRepository.existsByProductCode(cartCreateRequestDto.getProductCode())) {
            throw new BaseException(BaseResponseStatus.NO_EXIST_PRODUCT);
        }

        if (!productOptionListRepository.existsById(cartCreateRequestDto.getProductOptionListId())) {
            throw new BaseException(BaseResponseStatus.NO_EXIST_OPTION);
        }

        try {
            cartRepository.save(cartCreateRequestDto.toEntity(UUID.randomUUID().toString()));
        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.FAILED_TO_RESTORE);
        }
    }
}
