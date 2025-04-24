package com.example.shinsekai.notification.application;

import com.example.shinsekai.notification.dto.in.RestockNotificationRequestDto;
import com.example.shinsekai.notification.dto.out.RestockNotificationResponseDto;

import java.util.List;

public interface RestockNotificationService {

    List<RestockNotificationResponseDto> findAll();

    void register(String memberUuid,RestockNotificationRequestDto dto);

    void notifyForRestockedOption(Long productOptionId);
}
