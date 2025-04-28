package com.example.shinsekai.event.vo.in;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class EventCreateRequestVo {
    private String eventName;
    private String eventImage;
    private String eventImageAltText;
    private String eventThumbnailImage;
    private String eventThumbnailImageAltText;
    private LocalDate startDate;
    private LocalDate endDate;

    @Builder
    public EventCreateRequestVo(String eventName, String eventImage, String eventImageAltText,
                                String eventThumbnailImage, String eventThumbnailImageAltText,
                                LocalDate startDate, LocalDate endDate) {
        this.eventName = eventName;
        this.eventImage = eventImage;
        this.eventImageAltText = eventImageAltText;
        this.eventThumbnailImage = eventThumbnailImage;
        this.eventThumbnailImageAltText = eventThumbnailImageAltText;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
