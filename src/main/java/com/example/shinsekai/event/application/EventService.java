package com.example.shinsekai.event.application;

import com.example.shinsekai.event.dto.in.EventCreateRequestDto;
import com.example.shinsekai.event.dto.in.EventUpdateRequestDto;
import com.example.shinsekai.event.dto.out.EventGetDetailResponseDto;
import com.example.shinsekai.event.dto.out.EventGetTitleResponseDto;

import java.util.Arrays;
import java.util.List;

public interface EventService {
    void createEvent(EventCreateRequestDto eventCreateRequestDto);
    EventGetDetailResponseDto getEventDetail(Integer eventId);
    List<EventGetTitleResponseDto> getAllEventTitle();
    void updateEvent(EventUpdateRequestDto eventUpdateRequestDto);
    void deleteEvent(Integer eventId);
}
