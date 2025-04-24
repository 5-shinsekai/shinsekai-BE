package com.example.shinsekai.notification.vo.out;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class RestockNotificationResponseVo {

    private Long id;
    private String memberUuid;
    private Long productionOptionId;
    private LocalDateTime validUntil;
    private Boolean mailNotified;
    private Boolean sseNotified;

    @Builder
    public RestockNotificationResponseVo(Long id,
                                         String memberUuid,
                                         Long productionOptionId,
                                         LocalDateTime validUntil,
                                         Boolean mailNotified,
                                         Boolean sseNotified) {
        this.id = id;
        this.memberUuid = memberUuid;
        this.productionOptionId = productionOptionId;
        this.validUntil = validUntil;
        this.mailNotified = mailNotified;
        this.sseNotified = sseNotified;
    }
}
