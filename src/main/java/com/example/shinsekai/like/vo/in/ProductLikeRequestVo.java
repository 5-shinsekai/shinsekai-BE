package com.example.shinsekai.like.vo.in;

import lombok.Getter;

@Getter
public class ProductLikeRequestVo {

    private Long productLikeId;
    private String productCode;
    private Boolean isLiked;
}
