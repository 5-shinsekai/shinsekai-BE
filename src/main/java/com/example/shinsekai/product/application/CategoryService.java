package com.example.shinsekai.product.application;

import com.example.shinsekai.product.vo.out.MainCategorysGetResponseVo;

import java.util.List;

public interface CategoryService {
    List<MainCategorysGetResponseVo> getMainCategorysName();
}
