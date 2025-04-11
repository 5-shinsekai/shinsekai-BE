package com.example.shinsekai.card.dto.in;

import lombok.*;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UseStarbucksCardRequestDto {
    private String memberStarbucksCardUuid;
    private String memberUuid;
    private Double price;
}
