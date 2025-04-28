package com.example.shinsekai.option.presentation;

import com.example.shinsekai.common.entity.BaseResponseEntity;
import com.example.shinsekai.common.entity.BaseResponseStatus;
import com.example.shinsekai.option.application.SizeService;
import com.example.shinsekai.option.dto.in.SizeRequestDto;
import com.example.shinsekai.option.dto.out.SizeResponseDto;
import com.example.shinsekai.option.vo.in.SizeRequestVo;
import com.example.shinsekai.option.vo.out.SizeResponseVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Size", description = "사이즈")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/option/size")
public class SizeController {

    private final SizeService sizeService;

    @Operation(summary = "사이즈 등록", description = "새로운 사이즈를 등록합니다.")
    @PostMapping
    public BaseResponseEntity<Void> createSize(@RequestBody SizeRequestVo sizeRequestVo) {
        sizeService.createSize(SizeRequestDto.from(sizeRequestVo));
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }

    @Operation(summary = "사이즈 수정", description = "기존 사이즈를 수정합니다.")
    @PutMapping("/{id}")
    public BaseResponseEntity<Void> updateSize(
            @PathVariable Long id,
            @RequestBody SizeRequestVo sizeRequestVo) {
        sizeService.updateSize(SizeRequestDto.from(id, sizeRequestVo));
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }

    @Operation(summary = "사이즈 삭제", description = "특정 사이즈를 삭제합니다.")
    @DeleteMapping("/{id}")
    public BaseResponseEntity<Void> deleteSize(@PathVariable Long id) {
        sizeService.deleteSize(id);
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }

    @Operation(summary = "사이즈 전체 조회", description = "등록된 모든 사이즈 목록을 조회합니다.")
    @GetMapping
    public BaseResponseEntity<List<SizeResponseVo>> getAllSizes() {
        return new BaseResponseEntity<>(sizeService.getAllSizes().stream()
                .map(SizeResponseDto::toVo).toList());
    }

    @Operation(summary = "사이즈 단일 조회", description = "특정 사이즈(id 기준)의 상세 정보를 조회합니다.")
    @GetMapping("/{id}")
    public BaseResponseEntity<SizeResponseVo> getSizes(@PathVariable Long id) {
        return new BaseResponseEntity<>(sizeService.getSize(id).toVo());
    }
}
