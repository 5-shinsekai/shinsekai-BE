package com.example.shinsekai.sse.presentation;

import com.example.shinsekai.common.jwt.JwtTokenProvider;
import com.example.shinsekai.sse.application.SseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/api/v1/sse")
@RequiredArgsConstructor
public class SseController {

    private final SseService sseService;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter connect() {
        return sseService.connect(jwtTokenProvider.getMemberUuid());
    }
}
