package com.example.shinsekai.product.presentation;

import com.example.shinsekai.common.entity.BaseResponseEntity;
import com.example.shinsekai.product.application.CategoryServiceImpl;
import com.example.shinsekai.product.vo.out.MainCategorysGetResponseVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/category")
public class CategoryController {

    private final CategoryServiceImpl categoryService;

    @GetMapping
    public BaseResponseEntity<List<MainCategorysGetResponseVo>> getMainCategorysName() {
        return new BaseResponseEntity<>(categoryService.getMainCategorysName());
    }

}
