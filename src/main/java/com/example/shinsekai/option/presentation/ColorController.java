package com.example.shinsekai.option.presentation;

import com.example.shinsekai.common.entity.BaseResponseEntity;
import com.example.shinsekai.common.entity.BaseResponseStatus;
import com.example.shinsekai.option.application.ColorService;
import com.example.shinsekai.option.dto.in.ColorRequestDto;
import com.example.shinsekai.option.vo.in.ColorRequestVo;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Color", description = "상품 색상")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/option/color")
public class ColorController {

    private final ColorService colorService;

    @PostMapping
    public BaseResponseEntity<Void> createColor(@RequestBody ColorRequestVo colorRequestVo) {
        colorService.createColor(ColorRequestDto.from(colorRequestVo));
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }
}
