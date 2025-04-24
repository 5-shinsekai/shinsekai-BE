package com.example.shinsekai.sse.application;

import com.example.shinsekai.common.entity.BaseResponseStatus;
import com.example.shinsekai.notification.entity.RestockNotification;
import com.example.shinsekai.notification.infrastructure.RestockNotificationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
@Service
public class SseService {

    private final RestockNotificationRepository restockNotificationRepository;

    // 유저별 Emitter 저장소
    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();

    @Transactional
    public SseEmitter connect(String memberUuid) {
        SseEmitter emitter = new SseEmitter(5 * 60 * 1000L); // 5분 유효
        emitters.put(memberUuid, emitter);

        // 과거 알림 먼저 전송
        List<String> unreadNotifications = restockNotificationRepository.findProductNamesToNotifyByMember(memberUuid);
        unreadNotifications.forEach(msg -> {
            try {
                emitter.send(SseEmitter.event().name("restock").data(msg));
            } catch (IOException e) {
                emitter.completeWithError(e);
            }
        });

        // sse 보냄 체크
        List<RestockNotification> restockNotificationList = restockNotificationRepository.findByMemberUuidAndSseNotifiedIsFalse(memberUuid);
        restockNotificationList
                .stream()
                .filter(RestockNotification::getMailNotified)
                .forEach(RestockNotification::markAsSseNotified);

        // 연결 끊기거나 오류 나면 emitter 제거
        emitter.onCompletion(() -> emitters.remove(memberUuid));
        emitter.onTimeout(() -> emitters.remove(memberUuid));
        emitter.onError(e -> emitters.remove(memberUuid));

        try {
            emitter.send(SseEmitter.event().name("connected").data(BaseResponseStatus.SUCCESS_TO_SSE_CONNECT));
        } catch (IOException e) {
            emitter.completeWithError(e);
        }

        return emitter;
    }

    public void sendToMember(String memberUuid, String message) {
        SseEmitter emitter = emitters.get(memberUuid);
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event()
                        .name("restock")
                        .data(message));
            } catch (IOException e) {
                emitter.completeWithError(e);
                emitters.remove(memberUuid);
            }
        }
    }

    public boolean isUserConnected(String memberUuid) {
        return emitters.containsKey(memberUuid);
    }
}
