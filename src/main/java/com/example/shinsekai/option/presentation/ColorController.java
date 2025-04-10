package com.example.shinsekai.option.presentation;

import com.example.shinsekai.common.entity.BaseResponseEntity;
import com.example.shinsekai.common.entity.BaseResponseStatus;
import com.example.shinsekai.option.application.ColorService;
import com.example.shinsekai.option.dto.in.ColorRequestDto;
import com.example.shinsekai.option.dto.out.ColorResponseDto;
import com.example.shinsekai.option.vo.in.ColorRequestVo;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Color", description = "색상")
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

    @PutMapping("/{id}")
    public BaseResponseEntity<Void> updateColor(
            @PathVariable Long id,
            @RequestBody ColorRequestVo colorRequestVo) {
        colorService.updateColor(ColorRequestDto.from(id, colorRequestVo));
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }

    @DeleteMapping("/{id}")
    public BaseResponseEntity<Void> deleteColor(@PathVariable Long id) {
        colorService.deleteColor(id);
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }

    @GetMapping
    public BaseResponseEntity<List<ColorResponseDto>> getAllColors() {
        return new BaseResponseEntity<>(colorService.getAllColors());
    }

    @GetMapping("/{id}")
    public BaseResponseEntity<ColorResponseDto> getColor(@PathVariable Long id) {
        return new BaseResponseEntity<>(colorService.getColor(id));
    }
}
