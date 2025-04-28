package com.example.shinsekai.option.presentation;

import com.example.shinsekai.common.entity.BaseResponseEntity;
import com.example.shinsekai.common.entity.BaseResponseStatus;
import com.example.shinsekai.option.application.ColorService;
import com.example.shinsekai.option.dto.in.ColorRequestDto;
import com.example.shinsekai.option.dto.out.ColorResponseDto;
import com.example.shinsekai.option.vo.in.ColorRequestVo;
import com.example.shinsekai.option.vo.out.ColorResponseVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Color", description = "색상 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/option/color")
public class ColorController {

    private final ColorService colorService;

    @Operation(summary = "색상 등록", description = "새로운 색상을 등록합니다.")
    @PostMapping
    public BaseResponseEntity<Void> createColor(@RequestBody ColorRequestVo colorRequestVo) {
        colorService.createColor(ColorRequestDto.from(colorRequestVo));
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }

    @Operation(summary = "색상 수정", description = "기존 색상을 수정합니다.")
    @PutMapping("/{id}")
    public BaseResponseEntity<Void> updateColor(
            @PathVariable Long id,
            @RequestBody ColorRequestVo colorRequestVo) {
        colorService.updateColor(ColorRequestDto.from(id, colorRequestVo));
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }

    @Operation(summary = "색상 삭제", description = "특정 색상을 삭제합니다.")
    @DeleteMapping("/{id}")
    public BaseResponseEntity<Void> deleteColor(@PathVariable Long id) {
        colorService.deleteColor(id);
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }

    @Operation(summary = "색상 전체 조회", description = "등록된 모든 색상 목록을 조회합니다.")
    @GetMapping
    public BaseResponseEntity<List<ColorResponseVo>> getAllColors() {
        return new BaseResponseEntity<>(colorService.getAllColors().stream()
                .map(ColorResponseDto::toVo).toList());
    }

    @Operation(summary = "색상 단일 조회", description = "특정 색상(id 기준)의 상세 정보를 조회합니다.")
    @GetMapping("/{id}")
    public BaseResponseEntity<ColorResponseVo> getColor(@PathVariable Long id) {
        return new BaseResponseEntity<>(colorService.getColor(id).toVo());
    }
}
