package com.example.shinsekai.external.starbucksCard.presentation;

import com.example.shinsekai.external.starbucksCard.application.ExternalStarbucksCardService;
import com.example.shinsekai.external.starbucksCard.vo.ExternalStarbucksCardRequestVo;
import com.example.shinsekai.external.starbucksCard.vo.ExternalStarbucksCardResponseVo;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "external/starbucksCard", description = "스타벅스 카드 외부 API")
@RequiredArgsConstructor
@RequestMapping("/api/v1/external/starbucks-card")
@RestController
public class ExternalStarbucksCardController {
    /*
     * Todo : 스타벅스 카드 인증 외부 모듈
     *  실제 인증 구현 X
     *  스타벅스 카드 등록 시 카드 금액 및 이미지 랜덤 생성용으로 사용
     * */
    private final ExternalStarbucksCardService externalStarbucksCardService;

    @PostMapping
    public ExternalStarbucksCardResponseVo generateRandomCardInfo(@RequestBody ExternalStarbucksCardRequestVo externalStarbucksCardRequestVo) {
        return externalStarbucksCardService.generateRandomCardInfo(externalStarbucksCardRequestVo);
    }
}
