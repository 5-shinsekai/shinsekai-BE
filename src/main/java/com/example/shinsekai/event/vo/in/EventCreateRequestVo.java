package com.example.shinsekai.event.vo.in;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class EventCreateRequestVo {
    private String eventName;
    private String eventImage;
    private String eventImageAltText;
    private LocalDate startDate;
    private LocalDate endDate;

    @Builder
    public EventCreateRequestVo(String eventName, String eventImage, String eventImageAltText,
                                LocalDate startDate, LocalDate endDate) {
        this.eventName = eventName;
        this.eventImage = eventImage;
        this.eventImageAltText = eventImageAltText;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
