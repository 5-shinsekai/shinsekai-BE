package com.example.shinsekai.external.starbucksCard.dto;

import com.example.shinsekai.external.starbucksCard.vo.ExternalStarbucksCardRequestVo;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ExternalStarbucksCardRequestDto {
    private String cardNumber;
    private String pinNumber;

    public ExternalStarbucksCardRequestDto from(ExternalStarbucksCardRequestVo externalStarbucksCardRequestVo) {
        return ExternalStarbucksCardRequestDto.builder()
                .cardNumber(externalStarbucksCardRequestVo.getCardNumber())
                .pinNumber(externalStarbucksCardRequestVo.getPinNumber())
                .build();
    }
}
