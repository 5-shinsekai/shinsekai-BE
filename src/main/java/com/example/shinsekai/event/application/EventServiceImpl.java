package com.example.shinsekai.event.application;

import com.example.shinsekai.common.entity.BaseResponseStatus;
import com.example.shinsekai.common.exception.BaseException;
import com.example.shinsekai.event.dto.in.EventCreateRequestDto;
import com.example.shinsekai.event.dto.in.EventUpdateRequestDto;
import com.example.shinsekai.event.dto.out.EventGetDetailResponseDto;
import com.example.shinsekai.event.dto.out.EventGetThumbnailResponseDto;
import com.example.shinsekai.event.dto.out.EventGetTitleResponseDto;
import com.example.shinsekai.event.entity.Event;
import com.example.shinsekai.event.infrastructure.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    @Override
    public void createEvent(EventCreateRequestDto eventCreateRequestDto) {
        eventRepository.save(eventCreateRequestDto.toEntity());
    }

    @Override
    public EventGetDetailResponseDto getEventDetail(Integer eventId) {
        return EventGetDetailResponseDto.from(eventRepository.findOngoingEventById(eventId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_EVENT)));
    }

    @Override
    public List<EventGetTitleResponseDto> getAllEventTitle() {
        return eventRepository.findAllOngoingEvents(Pageable.unpaged())
                .stream()
                .map(EventGetTitleResponseDto::from)
                .toList();
    }

    @Override
    public List<EventGetThumbnailResponseDto> getAllEventThumbnail() {
        return eventRepository.findAllOngoingEvents(PageRequest.of(0, 6))
                .stream()
                .map(EventGetThumbnailResponseDto::from)
                .toList();
    }

    @Override
    public void updateEvent(EventUpdateRequestDto eventUpdateRequestDto) {
        Event event = eventRepository.findById(eventUpdateRequestDto.getEventId())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_EVENT));

        eventRepository.save(eventUpdateRequestDto.toEntity(event));
    }

    @Override
    public void deleteEvent(Integer eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_EVENT));

        eventRepository.delete(event);
    }
}
