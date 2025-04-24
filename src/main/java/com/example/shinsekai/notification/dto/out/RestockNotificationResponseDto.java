package com.example.shinsekai.notification.dto.out;

import com.example.shinsekai.notification.entity.RestockNotification;
import com.example.shinsekai.notification.vo.out.RestockNotificationResponseVo;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class RestockNotificationResponseDto {

    private Long id;
    private String memberUuid;
    private Long productionOptionId;
    private LocalDateTime validUntil;
    private Boolean mailNotified;
    private Boolean sseNotified;

    @Builder
    public RestockNotificationResponseDto(Long id,
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

    public static RestockNotificationResponseDto from(RestockNotification restockNotification) {
        return RestockNotificationResponseDto.builder()
                .id(restockNotification.getId())
                .memberUuid(restockNotification.getMemberUuid())
                .productionOptionId(restockNotification.getProductOptionId())
                .validUntil(restockNotification.getValidUntil())
                .mailNotified(restockNotification.getMailNotified())
                .sseNotified(restockNotification.getSseNotified())
                .build();
    }

    public RestockNotificationResponseVo toVo() {
        return RestockNotificationResponseVo.builder()
                .id(id)
                .memberUuid(memberUuid)
                .productionOptionId(productionOptionId)
                .validUntil(validUntil)
                .mailNotified(mailNotified)
                .sseNotified(sseNotified)
                .build();
    }

}
