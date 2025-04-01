package com.example.shinsekai.card.presentation;

import com.example.shinsekai.card.application.StarbucksCardService;
import com.example.shinsekai.card.dto.in.StarbucksCardRequestDto;
import com.example.shinsekai.card.dto.out.StarbucksCardResponseDto;
import com.example.shinsekai.card.vo.in.StarbucksCardRequestVo;
import com.example.shinsekai.card.vo.out.StarbucksCardResponseVo;
import com.example.shinsekai.common.entity.BaseResponseEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/starbucksCard")
public class StarbucksCardController {

    private final StarbucksCardService starbucksCardService;

    @GetMapping()
    public List<StarbucksCardResponseVo> getStarbucksCards() {
        /*
         * 임시 memberUuid 추가함
         * */
        String memberUuid = "temp-memberUuid";

        return starbucksCardService.getStarbucksCard(memberUuid).stream()
                .map(StarbucksCardResponseDto::toVo)
                .toList();
    }


    @PostMapping()
    public BaseResponseEntity<Void> createStarbucksCard(@RequestBody StarbucksCardRequestVo starbucksCardRequestVo) {
        /*
         * 임시 memberUuid 추가함
         * */
        String memberUuid = "temp-memberUuid";
        starbucksCardService.createStarbucksCard(memberUuid, StarbucksCardRequestDto.from(starbucksCardRequestVo));
        return new BaseResponseEntity<>();

    }

    @DeleteMapping("/{starbucksCardUuid}")
    public BaseResponseEntity<Void> deleteStarbucksCard(@PathVariable String starbucksCardUuid) {
        starbucksCardService.deleteStarbucksCard(starbucksCardUuid);
        return new BaseResponseEntity<>();
    }
}
