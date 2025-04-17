package com.example.shinsekai.event.vo.in;

import lombok.Builder;
import lombok.Getter;

@Getter
public class EventGetThumbnailResponseVo {
    private Integer id;
    private String eventThumbnailImage;
    private String eventThumbnailImageAltText;

    @Builder
    public EventGetThumbnailResponseVo(Integer id, String eventThumbnailImage, String eventThumbnailImageAltText) {
        this.id = id;
        this.eventThumbnailImage = eventThumbnailImage;
        this.eventThumbnailImageAltText = eventThumbnailImageAltText;
    }
}
