package com.example.shinsekai.event.dto.out;

import com.example.shinsekai.event.entity.Event;
import com.example.shinsekai.event.vo.out.EventGetDetailResponseVo;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class EventGetDetailResponseDto {
    private Integer eventId;
    private String eventName;
    private String eventImage;
    private String eventImageAltText;
    private LocalDate startDate;
    private LocalDate endDate;

    @Builder
    public EventGetDetailResponseDto(Integer eventId, String eventName, String eventImage, String eventImageAltText,
                                     LocalDate startDate, LocalDate endDate) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventImage = eventImage;
        this.eventImageAltText = eventImageAltText;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static EventGetDetailResponseDto from(Event event) {
        return EventGetDetailResponseDto.builder()
                .eventId(event.getId())
                .eventName(event.getEventName())
                .eventImage(event.getEventImage())
                .eventImageAltText(event.getEventImageAltText())
                .startDate(event.getStartDate())
                .endDate(event.getEndDate())
                .build();
    }

    public EventGetDetailResponseVo toVo() {
        return EventGetDetailResponseVo.builder()
                .eventId(eventId)
                .eventName(eventName)
                .eventImage(eventImage)
                .eventImageAltText(eventImageAltText)
                .startDate(startDate)
                .endDate(endDate)
                .build();
    }
}
