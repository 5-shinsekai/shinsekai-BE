package com.example.shinsekai.card.dto.in;

import com.example.shinsekai.card.entity.MemberStarbucksCardList;
import com.example.shinsekai.card.entity.StarbucksCard;
import lombok.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class MemberStarbucksListDto {
    private String memberStarbucksCardUuid;
    private String memberUuid;

    public MemberStarbucksCardList toEntity(StarbucksCard starbucksCard){
        return MemberStarbucksCardList.builder()
                .memberStarbucksCardUuid(generateMemberStarbucksCardCode())
                .starbucksCard(starbucksCard)
                .memberUuid(memberUuid)
                .build();
    }

    public static String generateMemberStarbucksCardCode() {
        String prefix = "MS";
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String uuidPart = UUID.randomUUID().toString().substring(0, 8);

        return prefix + date + "-" + uuidPart;
    }
}
