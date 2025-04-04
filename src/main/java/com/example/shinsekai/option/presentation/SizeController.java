package com.example.shinsekai.option.presentation;

import com.example.shinsekai.common.entity.BaseResponseEntity;
import com.example.shinsekai.common.entity.BaseResponseStatus;
import com.example.shinsekai.option.application.SizeService;
import com.example.shinsekai.option.dto.in.SizeRequestDto;
import com.example.shinsekai.option.vo.in.SizeRequestVo;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Size", description = "사이즈")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/option/size")
public class SizeController {

    private final SizeService sizeService;

    @PostMapping
    public BaseResponseEntity<Void> createSize(@RequestBody SizeRequestVo sizeRequestVo) {
        sizeService.createSize(SizeRequestDto.from(sizeRequestVo));
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }

    @PutMapping("/{id}")
    public BaseResponseEntity<Void> updateSize(@PathVariable Long id, @RequestBody SizeRequestVo sizeRequestVo) {
        sizeService.updateSize(SizeRequestDto.from(id, sizeRequestVo));
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }
}
