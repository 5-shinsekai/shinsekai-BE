package com.example.shinsekai.card.presentation.controller;

import com.example.shinsekai.card.application.StarbucksCardKeyMapService;
import com.example.shinsekai.card.application.StarbucksCardService;
import com.example.shinsekai.card.dto.in.StarbucksCardRequestDto;
import com.example.shinsekai.card.dto.out.StarbucksCardResponseDto;
import com.example.shinsekai.card.vo.StarbucksCardRequestVo;
import com.example.shinsekai.card.vo.StarbucksCardResponseVo;
import com.example.shinsekai.common.entity.BaseResponseEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/v1/starbucksCard")
@RequiredArgsConstructor
public class StarbucksCardController {

    private final StarbucksCardService starbucksCardService;
    private final StarbucksCardKeyMapService starbucksCardKeyMapService;

    @GetMapping()
    public List<StarbucksCardResponseVo> getStarbucksCards() {
        /*
        * 임시 memberUuid 추가함
        * */
        String memberUuid = "7d8eb4ca-d560-4f45-ba76-ef9b6ce6346f";

        return starbucksCardService.getStarbucksCard(memberUuid).stream()
                .map(StarbucksCardResponseDto::toVo)
                .collect(Collectors.toList());
    }




    @PostMapping()
    public BaseResponseEntity<Void> createStarbucksCard(@RequestBody StarbucksCardRequestVo starbucksCardRequestVo) {

        starbucksCardService.createStarbucksCard(StarbucksCardRequestDto.from(starbucksCardRequestVo));
        return new BaseResponseEntity<>();

    }
}
