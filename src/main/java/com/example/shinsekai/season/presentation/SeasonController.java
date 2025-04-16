package com.example.shinsekai.season.presentation;

import com.example.shinsekai.common.entity.BaseResponseEntity;
import com.example.shinsekai.common.entity.BaseResponseStatus;
import com.example.shinsekai.season.application.SeasonServiceImpl;
import com.example.shinsekai.season.dto.in.SeasonCreateRequestDto;
import com.example.shinsekai.season.dto.in.SeasonUpdateRequestDto;
import com.example.shinsekai.season.dto.out.SeasonGetResponseDto;
import com.example.shinsekai.season.vo.in.SeasonCreateRequestVo;
import com.example.shinsekai.season.vo.in.SeasonUpdateRequestVo;
import com.example.shinsekai.season.vo.out.SeasonGetResponseVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Season", description = "시즌 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/season")
public class SeasonController {

    private final SeasonServiceImpl seasonService;

    @Operation(summary = "시즌 생성")
    @PostMapping
    public BaseResponseEntity<Void> createSeason(@RequestBody SeasonCreateRequestVo seasonCreateRequestVo) {
        seasonService.createSeason(SeasonCreateRequestDto.from(seasonCreateRequestVo));
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }

    @Operation(summary = "시즌 단일 조회")
    @GetMapping("/{seasonId}")
    public BaseResponseEntity<SeasonGetResponseVo> getSeason(@PathVariable Integer seasonId) {
        return new BaseResponseEntity<>(seasonService.getSeason(seasonId).toVo());
    }

    @Operation(summary = "시즌 전체 조회")
    @GetMapping
    public BaseResponseEntity<List<SeasonGetResponseVo>> getAllSeason() {
        return new BaseResponseEntity<>(seasonService.getAllSeason().stream()
                .map(SeasonGetResponseDto::toVo).toList());
    }

    @Operation(summary = "시즌 수정")
    @PutMapping
    public BaseResponseEntity<Void> updateSeason(@RequestBody SeasonUpdateRequestVo seasonUpdateRequestVo) {
        seasonService.updateSeason(SeasonUpdateRequestDto.from(seasonUpdateRequestVo));
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }

    @Operation(summary = "시즌 하드 삭제")
    @DeleteMapping("/{seasonId}")
    public BaseResponseEntity<Void> deleteSeason(@PathVariable Integer seasonId) {
        seasonService.deleteSeason(seasonId);
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }
}
