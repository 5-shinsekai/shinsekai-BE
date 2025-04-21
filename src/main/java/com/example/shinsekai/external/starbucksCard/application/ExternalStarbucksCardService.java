package com.example.shinsekai.external.starbucksCard.application;

import com.example.shinsekai.external.starbucksCard.dto.ExternalStarbucksCardResponseDto;
import com.example.shinsekai.external.starbucksCard.vo.ExternalStarbucksCardRequestVo;
import com.example.shinsekai.external.starbucksCard.vo.ExternalStarbucksCardResponseVo;
import org.springframework.stereotype.Service;

@Service
public class ExternalStarbucksCardService {
    public ExternalStarbucksCardResponseVo generateRandomCardInfo(ExternalStarbucksCardRequestVo externalStarbucksCardRequestVo) {
        int randomRemainAmount = (int)(Math.random() * 10) + 1;
        int randomImage = (int)(Math.random() * 5);
        String[] cardImages = {"20250228/011768", "20250228/011767", "20250228/011766","20241213/012107","20241213/012106"};

        return ExternalStarbucksCardResponseDto.builder()
                .cardName(externalStarbucksCardRequestVo.getCardName())
                .cardNumber(externalStarbucksCardRequestVo.getCardNumber())
                .remainAmount(randomRemainAmount * 10000.0)
                .cardImageUrl("https://image.istarbucks.co.kr/cardImg/"+cardImages[randomImage]+"_WEB.png")
                .cardDescription("스타벅스카드 이미지" + randomImage)
                .build().toVo();
    }
}
