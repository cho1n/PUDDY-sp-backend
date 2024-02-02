package sideproject.puddy.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sideproject.puddy.dto.chat.response.ChatDetailResponse;
import sideproject.puddy.dto.chat.response.ChatDogResponse;
import sideproject.puddy.dto.chat.response.ChatPerson;
import sideproject.puddy.dto.message.MessageDto;
import sideproject.puddy.model.Chat;
import sideproject.puddy.model.Dog;
import sideproject.puddy.model.Person;
import sideproject.puddy.security.util.SecurityUtil;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatAndMessageService {
    private final AuthService authService;
    private final ChatService chatService;
    private final MessageService messageService;
    private final DogService dogService;
    public ResponseEntity<ChatDetailResponse> enterChat(Long chatId){
        Long currentUserId = SecurityUtil.getCurrentUserId();
        Person currentUser = authService.findById(currentUserId);
        chatService.enterChat(chatId.toString());
        Chat chat = chatService.findById(chatId);
        Person person = (currentUser == chat.getFirstPerson()) ? chat.getSecondPerson() : chat.getFirstPerson();
        if (person != null){
            Dog dog = dogService.findByPersonAndMain(person);
            List<MessageDto> messages = messageService.loadMessage(chatId);
            ChatDetailResponse chatDetailResponse = new ChatDetailResponse(
                    chatId, currentUserId, new ChatPerson(person.isGender(), new ChatDogResponse(dog.getName(), dog.getImage())), messages
            );
            return ResponseEntity.ok().body(chatDetailResponse);
        }
        else{
            List<MessageDto> messages = messageService.loadMessage(chatId);
            ChatDetailResponse chatDetailResponse = new ChatDetailResponse(
                    chatId, currentUserId, null, messages
            );
            return ResponseEntity.ok().body(chatDetailResponse);
        }
    }
}
