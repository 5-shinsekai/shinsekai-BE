package com.example.shinsekai.card.vo.in;

import lombok.Getter;

@Getter
public class TransferStarbucksCardVo {
    private String sourceMemberStarbucksCardUuid;
    private String targetMemberStarbucksCardUuid;
    private Double remainAmount;
}
