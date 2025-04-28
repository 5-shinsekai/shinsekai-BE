package com.example.shinsekai.card.dto.in;

import com.example.shinsekai.card.entity.StarbucksCard;
import com.example.shinsekai.card.vo.in.StarbucksCardRequestVo;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Getter
@Builder
@ToString
public class StarbucksCardRequestDto {
    private String memberUuid;
    private String cardUuid;
    private String cardName;
    private String cardNumber;
    private String cardImageUrl;
    private String cardDescription;
    private Double remainAmount;
    private Boolean agreed;


    public StarbucksCard toEntity() {
        return StarbucksCard.builder()
                .cardUuid(generateStarbucksCardCode())
                .cardName(cardName)
                .cardNumber(cardNumber)
                .remainAmount(remainAmount)
                .cardImageUrl(cardImageUrl)
                .cardDescription(cardDescription)
                .agreed(agreed)
                .build();
    }

    public static StarbucksCardRequestDto from(StarbucksCardRequestVo starbucksCardRequestVo, String memberUuid) {
        return StarbucksCardRequestDto.builder()
                .memberUuid(memberUuid)
                .cardName(starbucksCardRequestVo.getCardName())
                .cardNumber(starbucksCardRequestVo.getCardNumber())
                .remainAmount(starbucksCardRequestVo.getRemainAmount())
                .cardImageUrl(starbucksCardRequestVo.getCardImageUrl())
                .cardDescription(starbucksCardRequestVo.getCardDescription())
                .agreed(starbucksCardRequestVo.getAgreed())
                .build();
    }

    public static String generateStarbucksCardCode() {
        String prefix = "S";
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String uuidPart = UUID.randomUUID().toString().substring(0, 8);

        return prefix + date + "-" + uuidPart;
    }
}
