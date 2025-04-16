package com.example.shinsekai.notification.vo.in;

import lombok.Getter;

@Getter
public class RestockNotificationRequestVo {
    private Long productOptionId;
    private int durationDays;
}
