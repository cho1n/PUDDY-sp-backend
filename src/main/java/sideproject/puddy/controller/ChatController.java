package sideproject.puddy.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sideproject.puddy.dto.chat.response.GetChatListResonse;
import sideproject.puddy.service.ChatService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ChatController {
    private final ChatService chatService;
    @GetMapping("/chat")
    public GetChatListResonse getChatList(){
        return chatService.getChatList();
    }
    @GetMapping("/chat/{chatId}")
    public ResponseEntity<String> enterChat(@PathVariable String chatId){
        return chatService.enterChat(chatId);
    }
}
