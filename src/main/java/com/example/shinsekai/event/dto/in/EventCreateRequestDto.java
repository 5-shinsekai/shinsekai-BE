package com.example.shinsekai.event.dto.in;

import com.example.shinsekai.event.entity.Event;
import com.example.shinsekai.event.vo.in.EventCreateRequestVo;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class EventCreateRequestDto {
    private String eventName;
    private String eventImage;
    private String eventImageAltText;
    private LocalDate startDate;
    private LocalDate endDate;

    @Builder
    public EventCreateRequestDto(String eventName, String eventImage, String eventImageAltText,
                                 LocalDate startDate, LocalDate endDate) {
        this.eventName = eventName;
        this.eventImage = eventImage;
        this.eventImageAltText = eventImageAltText;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static EventCreateRequestDto from(EventCreateRequestVo vo) {
        return EventCreateRequestDto.builder()
                .eventName(vo.getEventName())
                .eventImage(vo.getEventImage())
                .eventImageAltText(vo.getEventImageAltText())
                .startDate(vo.getStartDate())
                .endDate(vo.getEndDate())
                .build();
    }

    public Event toEntity() {
        return Event.builder()
                .eventName(eventName)
                .eventImage(eventImage)
                .eventImageAltText(eventImageAltText)
                .startDate(startDate)
                .endDate(endDate)
                .build();
    }
}
