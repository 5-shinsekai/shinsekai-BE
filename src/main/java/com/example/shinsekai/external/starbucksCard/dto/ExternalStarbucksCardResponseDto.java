package com.example.shinsekai.external.starbucksCard.dto;

import com.example.shinsekai.external.starbucksCard.vo.ExternalStarbucksCardResponseVo;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ExternalStarbucksCardResponseDto {
    private String cardNumber;
    private Double remainAmount;
    private String cardImageUrl;
    private String cardDescription;

    public ExternalStarbucksCardResponseVo toVo(){
        return ExternalStarbucksCardResponseVo.builder()
                .cardNumber(cardNumber)
                .remainAmount(remainAmount)
                .cardImageUrl(cardImageUrl)
                .cardDescription(cardDescription)
                .build();
    }
}
