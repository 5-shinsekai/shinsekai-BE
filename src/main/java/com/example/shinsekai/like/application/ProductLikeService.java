package com.example.shinsekai.like.application;

import com.example.shinsekai.like.dto.in.ProductLikeRequestDto;
import com.example.shinsekai.like.dto.out.ProductLikeResponseDto;

import java.util.List;

public interface ProductLikeService {

    List<ProductLikeResponseDto> getLikeList(String memberUuid);
    void toggleLike(ProductLikeRequestDto productLikeRequestDto);

}
