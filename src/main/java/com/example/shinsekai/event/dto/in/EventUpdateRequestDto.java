package com.example.shinsekai.event.dto.in;

import com.example.shinsekai.event.entity.Event;
import com.example.shinsekai.event.vo.in.EventUpdateRequestVo;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class EventUpdateRequestDto {
    private Integer eventId;
    private String eventName;
    private String eventImage;
    private String eventImageAltText;
    private LocalDate startDate;
    private LocalDate endDate;

    @Builder
    public EventUpdateRequestDto(Integer eventId, String eventName, String eventImage,
                                 String eventImageAltText, LocalDate startDate, LocalDate endDate) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventImage = eventImage;
        this.eventImageAltText = eventImageAltText;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static EventUpdateRequestDto from(EventUpdateRequestVo vo) {
        return EventUpdateRequestDto.builder()
                .eventId(vo.getEventId())
                .eventName(vo.getEventName())
                .eventImage(vo.getEventImage())
                .eventImageAltText(vo.getEventImageAltText())
                .startDate(vo.getStartDate())
                .endDate(vo.getEndDate())
                .build();
    }

    public Event toEntity(Event event) {
        return Event.builder()
                .id(eventId)
                .eventName(eventName == null ? event.getEventName() : eventName)
                .eventImage(eventImage == null ? event.getEventImage() : eventImage)
                .eventImageAltText(eventImageAltText == null ? event.getEventImageAltText() : eventImageAltText)
                .startDate(startDate == null ? event.getStartDate() : startDate)
                .endDate(endDate == null ? event.getEndDate() : endDate)
                .build();
    }
}
