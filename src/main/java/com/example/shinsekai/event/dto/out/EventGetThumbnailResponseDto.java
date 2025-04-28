package com.example.shinsekai.event.dto.out;

import com.example.shinsekai.event.entity.Event;
import com.example.shinsekai.event.vo.in.EventGetThumbnailResponseVo;
import lombok.Builder;
import lombok.Getter;

@Getter
public class EventGetThumbnailResponseDto {
    private Integer id;
    private String eventThumbnailImage;
    private String eventThumbnailImageAltText;

    @Builder
    public EventGetThumbnailResponseDto(Integer id, String eventThumbnailImage, String eventThumbnailImageAltText) {
        this.id = id;
        this.eventThumbnailImage = eventThumbnailImage;
        this.eventThumbnailImageAltText = eventThumbnailImageAltText;
    }

    public EventGetThumbnailResponseVo toVo() {
        return EventGetThumbnailResponseVo.builder()
                .id(id)
                .eventThumbnailImage(eventThumbnailImage)
                .eventThumbnailImageAltText(eventThumbnailImageAltText)
                .build();
    }

    public static EventGetThumbnailResponseDto from(Event event) {
        return EventGetThumbnailResponseDto.builder()
                .id(event.getId())
                .eventThumbnailImage(event.getEventThumbnailImage())
                .eventThumbnailImageAltText(event.getEventThumbnailImageAltText())
                .build();
    }
}
