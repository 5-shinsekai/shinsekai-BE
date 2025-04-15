package com.example.shinsekai.event.vo.in;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class EventUpdateRequestVo {
    private Integer eventId;
    private String eventName;
    private String eventImage;
    private String eventImageAltText;
    private LocalDate startDate;
    private LocalDate endDate;

    @Builder
    public EventUpdateRequestVo(Integer eventId, String eventName, String eventImage, String eventImageAltText,
                                LocalDate startDate, LocalDate endDate) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventImage = eventImage;
        this.eventImageAltText = eventImageAltText;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
