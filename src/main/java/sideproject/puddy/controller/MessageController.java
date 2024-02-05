package sideproject.puddy.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import sideproject.puddy.dto.message.MessageDto;
import sideproject.puddy.dto.message.request.PostMessageRequest;
import sideproject.puddy.service.MessageService;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @MessageMapping("/message")
    public ResponseEntity<String> message(PostMessageRequest postMessageRequest) {
        log.info("{}", postMessageRequest.getCurrentUserId());
        messageService.onMessage(postMessageRequest);
        return ResponseEntity.ok().body("ok");
    }
    @GetMapping("/api/chat/{chatId}/message")
    public ResponseEntity<List<MessageDto>> loadMessage(@PathVariable Long chatId){
        return ResponseEntity.ok(messageService.loadMessage(chatId));
    }
}
