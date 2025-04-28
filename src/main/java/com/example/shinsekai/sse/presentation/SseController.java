package com.example.shinsekai.sse.presentation;

import com.example.shinsekai.common.jwt.JwtTokenProvider;
import com.example.shinsekai.sse.application.SseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Tag(name = "SSE")
@RestController
@RequestMapping("/api/v1/sse")
@RequiredArgsConstructor
public class SseController {

    private final SseService sseService;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping(value = "/{memberUuid}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter connect(@PathVariable String memberUuid) {
        return sseService.connect(memberUuid);
    }
}
