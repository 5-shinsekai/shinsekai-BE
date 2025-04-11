package com.example.shinsekai.external.starbucksCard.application;

import com.example.shinsekai.external.starbucksCard.dto.ExternalStarbucksCardResponseDto;
import com.example.shinsekai.external.starbucksCard.vo.ExternalStarbucksCardRequestVo;
import com.example.shinsekai.external.starbucksCard.vo.ExternalStarbucksCardResponseVo;
import org.springframework.stereotype.Service;

@Service
public class ExternalStarbucksCardService {
    public ExternalStarbucksCardResponseVo generateRandomCardInfo(ExternalStarbucksCardRequestVo externalStarbucksCardRequestVo) {
        int randomRemainAmount = (int)(Math.random() * 10) + 1;
        int randomImage = (int)(Math.random() * 3) + 1;

        return ExternalStarbucksCardResponseDto.builder()
                .cardName(externalStarbucksCardRequestVo.getCardName())
                .cardNumber(externalStarbucksCardRequestVo.getCardNumber())
                .remainAmount(randomRemainAmount * 10000.0)
                .cardImageUrl("/images/cards/starbucksCard" + randomImage + ".png")
                .cardDescription("스타벅스카드 이미지" + randomImage)
                .build().toVo();
    }
}
