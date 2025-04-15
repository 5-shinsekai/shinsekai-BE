package com.example.shinsekai.event.dto.out;

import com.example.shinsekai.event.entity.Event;
import com.example.shinsekai.event.vo.out.EventGetTitleResponseVo;
import lombok.Builder;
import lombok.Getter;

@Getter
public class EventGetTitleResponseDto {
    private int eventId;
    private String eventName;

    @Builder
    public EventGetTitleResponseDto(int eventId, String eventName) {
        this.eventId = eventId;
        this.eventName = eventName;
    }

    public static EventGetTitleResponseDto from(Event event) {
        return EventGetTitleResponseDto.builder()
                .eventId(event.getId())
                .eventName(event.getEventName())
                .build();
    }

    public EventGetTitleResponseVo toVo() {
        return EventGetTitleResponseVo.builder()
                .eventId(eventId)
                .eventName(eventName)
                .build();
    }
}
