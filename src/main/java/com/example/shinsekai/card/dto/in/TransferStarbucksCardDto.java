package com.example.shinsekai.card.dto.in;

import com.example.shinsekai.card.vo.in.TransferStarbucksCardVo;
import lombok.*;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TransferStarbucksCardDto {
    private String sourceMemberStarbucksCardUuid;
    private String targetMemberStarbucksCardUuid;
    private Double remainAmount;
    private String memeberUuid;

    public static TransferStarbucksCardDto from(TransferStarbucksCardVo vo, String memberUuid) {
        return TransferStarbucksCardDto.builder()
                .sourceMemberStarbucksCardUuid(vo.getSourceMemberStarbucksCardUuid())
                .targetMemberStarbucksCardUuid(vo.getTargetMemberStarbucksCardUuid())
                .remainAmount(vo.getRemainAmount())
                .memeberUuid(memberUuid)
                .build();
    }
}
