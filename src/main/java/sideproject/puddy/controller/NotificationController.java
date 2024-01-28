package sideproject.puddy.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import sideproject.puddy.security.util.SecurityUtil;
import sideproject.puddy.service.NotificationService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;
    public static Map<Long, SseEmitter> sseEmitters = new ConcurrentHashMap<>();        // 1. 모든 Emitters를 저장하는 ConcurrentHashMap

    @GetMapping("/api/notification/subscribe")
    public SseEmitter subscribe() {
        Long userId = SecurityUtil.getCurrentUserId();

        return notificationService.subscribe(userId);
    }
}