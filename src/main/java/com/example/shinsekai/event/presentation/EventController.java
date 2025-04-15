package com.example.shinsekai.event.presentation;

import com.example.shinsekai.common.entity.BaseResponseEntity;
import com.example.shinsekai.common.entity.BaseResponseStatus;
import com.example.shinsekai.event.application.EventServiceImpl;
import com.example.shinsekai.event.dto.in.EventCreateRequestDto;
import com.example.shinsekai.event.dto.in.EventUpdateRequestDto;
import com.example.shinsekai.event.dto.out.EventGetTitleResponseDto;
import com.example.shinsekai.event.vo.in.EventCreateRequestVo;
import com.example.shinsekai.event.vo.in.EventUpdateRequestVo;
import com.example.shinsekai.event.vo.out.EventGetDetailResponseVo;
import com.example.shinsekai.event.vo.out.EventGetTitleResponseVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Event", description = "기획전 관련 API")
@RequestMapping("/api/v1/event")
@RestController
@RequiredArgsConstructor
public class EventController {

    private final EventServiceImpl eventService;

    @Operation(summary = "기획전 생성")
    @PostMapping
    public BaseResponseEntity<Void> createEvent(@RequestBody EventCreateRequestVo eventCreateRequestVo) {
        eventService.createEvent(EventCreateRequestDto.from(eventCreateRequestVo));
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }

    @Operation(summary = "기획전 상세 조회")
    @GetMapping("/{eventId}")
    public BaseResponseEntity<EventGetDetailResponseVo> getEventDetail(@PathVariable Integer eventId) {
        return new BaseResponseEntity<>(eventService.getEventDetail(eventId).toVo());
    }

    @Operation(summary = "기획전 타이틀 전체 조회")
    @GetMapping
    public BaseResponseEntity<List<EventGetTitleResponseVo>> getAllEventTitle() {
        return new BaseResponseEntity<>(eventService.getAllEventTitle().stream()
                .map(EventGetTitleResponseDto::toVo).toList());
    }

    @Operation(summary = "기획전 수정")
    @PutMapping
    public BaseResponseEntity<Void> updateEvent(@RequestBody EventUpdateRequestVo eventUpdateRequestVo) {
        eventService.updateEvent(EventUpdateRequestDto.from(eventUpdateRequestVo));
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }

    @Operation(summary = "기획전 하드 삭제")
    @DeleteMapping("/{eventId}")
    public BaseResponseEntity<Void> deleteEvent(@PathVariable Integer eventId) {
        eventService.deleteEvent(eventId);
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }
}
