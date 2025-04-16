package com.example.shinsekai.notification.application;

import com.example.shinsekai.notification.dto.in.RestockNotificationRequestDto;

public interface RestockNotificationService {

    void register(String memberUuid,RestockNotificationRequestDto dto);

    void notifyForRestockedOption(Long productOptionId);
}
