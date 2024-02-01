package sideproject.puddy.service;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sideproject.puddy.dto.chat.ChatDto;
import sideproject.puddy.dto.chat.response.ChatDogResponse;
import sideproject.puddy.dto.chat.response.ChatResponse;
import sideproject.puddy.dto.chat.response.GetChatListResonse;
import sideproject.puddy.model.Chat;
import sideproject.puddy.model.Person;
import sideproject.puddy.redis.RedisSubscriber;
import sideproject.puddy.repository.ChatRepository;
import sideproject.puddy.security.util.SecurityUtil;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatService {
    private final AuthService authService;
    private final ChatRepository chatRepository;
    private final DogService dogService;
    private Map<String, ChannelTopic> topics;
    private final RedisMessageListenerContainer redisMessageListener;
    private final RedisSubscriber redisSubscriber;
    private final RedisTemplate<String, Object> redisTemplate;

    private HashOperations<String, String, ChatDto> opsHashMessageRoom;
    @PostConstruct
    private void init() {
        opsHashMessageRoom = redisTemplate.opsForHash();
        topics = new HashMap<>();
    }

    public GetChatListResonse getChatList(){
        Person person = authService.findById(SecurityUtil.getCurrentUserId());
        List<ChatResponse> chatList = chatRepository.findAllByFirstPersonOrSecondPersonOrderByCreatedAt(person, person).stream().map(chat ->
                        new ChatResponse(
                                chat.getId(),
                                chat.getSecondPerson().getId(),
                                chat.getSecondPerson().isGender(),
                                new ChatDogResponse(
                                        dogService.findByPersonAndMain(chat.getSecondPerson()).getName(),
                                        dogService.findByPersonAndMain(chat.getSecondPerson()).getImage()
                                ))).toList();
        return new GetChatListResonse(chatList);
    }

    @Transactional
    public void saveChat(Person receiver, Person sender) {
        if (!chatRepository.existsByFirstPersonAndSecondPerson(receiver, sender)
                && !chatRepository.existsByFirstPersonAndSecondPerson(sender, receiver)){
            Chat chat = chatRepository.save(new Chat(receiver, sender));
            ChatDto chatDto = new ChatDto(chat);
            opsHashMessageRoom.put("MESSAGE_ROOM", chat.getId().toString(), chatDto);
        }
    }
    public ResponseEntity<String> enterChat(String chatId){
        if(!topics.containsKey(chatId) && chatRepository.existsById(Long.valueOf(chatId))) {
            ChannelTopic topic = new ChannelTopic(chatId);
            redisMessageListener.addMessageListener(redisSubscriber, topic);
            topics.put(chatId, topic);
        }
        return ResponseEntity.ok().body("ok");
    }
    public ChannelTopic getTopic(String roomId) {
        return topics.get(roomId);
    }
}
