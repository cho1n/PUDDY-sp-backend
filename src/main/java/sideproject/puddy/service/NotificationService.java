package sideproject.puddy.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import sideproject.puddy.controller.NotificationController;
import sideproject.puddy.model.Person;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {
    private final AuthService authService;

    public SseEmitter subscribe(Long userId) {
        SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
        try {
            sseEmitter.send(SseEmitter.event().name("connect"));
        } catch (IOException e) {
            log.info(e.toString());
        }
        NotificationController.sseEmitters.put(userId, sseEmitter);
        sseEmitter.onCompletion(() -> NotificationController.sseEmitters.remove(userId));	// sseEmitter 연결이 완료될 경우
        sseEmitter.onTimeout(() -> NotificationController.sseEmitters.remove(userId));		// sseEmitter 연결에 타임아웃이 발생할 경우
        sseEmitter.onError((e) -> NotificationController.sseEmitters.remove(userId));		// sseEmitter 연결에 오류가 발생할 경우

        return sseEmitter;
    }

    public void notifyAlert(Long receiverId) {
        Person person = authService.findById(receiverId);
        Long userId = person.getId();
        if (NotificationController.sseEmitters.containsKey(userId)) {
            SseEmitter sseEmitterReceiver = NotificationController.sseEmitters.get(userId);
            try {
                sseEmitterReceiver.send(SseEmitter.event().name("addAlert").data("매칭신청이 왔습니다."));
            } catch (Exception e) {
                NotificationController.sseEmitters.remove(userId);
            }
        }
    }
}