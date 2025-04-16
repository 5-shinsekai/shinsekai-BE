package com.example.shinsekai.notification.dto.in;

import com.example.shinsekai.notification.vo.in.RestockNotificationRequestVo;
import lombok.Builder;
import lombok.Getter;

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
}
