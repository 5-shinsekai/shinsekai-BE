package com.example.shinsekai.notification.dto.in;

import com.example.shinsekai.notification.entity.RestockNotification;
import com.example.shinsekai.notification.vo.in.RestockNotificationRequestVo;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class RestockNotificationRequestDto {
    private Long productOptionId;
    private int durationDays;

    public static RestockNotificationRequestDto from(RestockNotificationRequestVo vo) {
        return RestockNotificationRequestDto.builder()
                .productOptionId(vo.getProductOptionId())
                .durationDays(vo.getDurationDays())
                .build();
    }

    public RestockNotification toEntity(String memberUuid) {
        return RestockNotification.builder()
                .memberUuid(memberUuid)
                .productOptionId(productOptionId)
                .requestedAt(LocalDateTime.now())
                .validUntil(LocalDateTime.now().plusDays(durationDays))
                .build();
    }
}
