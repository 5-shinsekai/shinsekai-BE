package com.example.shinsekai.event.vo.out;

import lombok.Builder;
import lombok.Getter;

@Getter
public class EventGetTitleResponseVo {
    private Integer eventId;
    private String eventName;

    @Builder
    public EventGetTitleResponseVo(Integer eventId, String eventName) {
        this.eventId = eventId;
        this.eventName = eventName;
    }
}
