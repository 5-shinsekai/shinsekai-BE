package com.example.shinsekai.card.presentation.controller;

import com.example.shinsekai.card.application.StarbucksCardKeyMapService;
import com.example.shinsekai.card.dto.in.StarbucksCardKeyMapRequestDto;
import com.example.shinsekai.card.entity.StarbucksCardKeyMap;
import com.example.shinsekai.card.vo.StarbucksCardKeyMapRequestVo;
import com.example.shinsekai.card.vo.StarbucksCardRequestVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/starbuckscardKeyMap")
public class StarbucksCardKeyMapController {

    private final StarbucksCardKeyMapService starbucksCardKeyMapService;

    @PostMapping()
    public String createStarbucksCardKeyMap( @RequestBody StarbucksCardKeyMapRequestVo starbucksCardKeyMapRequestVo){
        starbucksCardKeyMapService.createStarbucksCardKeyMap(StarbucksCardKeyMapRequestDto.from(starbucksCardKeyMapRequestVo));
        return "등록";
    }

}
