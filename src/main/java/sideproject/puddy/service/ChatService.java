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
import sideproject.puddy.dto.chat.response.ChatPerson;
import sideproject.puddy.dto.chat.response.ChatResponse;
import sideproject.puddy.dto.chat.response.GetChatListResponse;
import sideproject.puddy.exception.CustomException;
import sideproject.puddy.exception.ErrorCode;
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

    public GetChatListResponse getChatList(){
        Person person = authService.findById(SecurityUtil.getCurrentUserId());
        List<ChatResponse> chatList = chatRepository.findAllByFirstPersonOrSecondPersonOrderByCreatedAt(person, person).stream().map(chat ->
                        findChat(person, chat)).toList();
        return new GetChatListResponse(chatList);
    }
    private ChatResponse findChat(Person person, Chat chat){
        Person receiver = (person == chat.getSecondPerson() ? chat.getFirstPerson() : chat.getSecondPerson());
        return findNull(receiver, chat);
    }
    private ChatResponse findNull(Person person, Chat chat){
        if (person != null) {
            return new ChatResponse(chat.getId(), new ChatPerson(person.getId(), person.isGender(),
                    new ChatDogResponse(dogService.findByPersonAndMain(person).getName(),
                            dogService.findByPersonAndMain(person).getImage())));
        } else {
            return new ChatResponse(chat.getId(), null);
        }
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
    public void enterChat(String chatId){
        if (!topics.containsKey(chatId)) {
            if (chatRepository.existsById(Long.valueOf(chatId))) {
                ChannelTopic topic = new ChannelTopic(chatId);
                redisMessageListener.addMessageListener(redisSubscriber, topic);
                topics.put(chatId, topic);
            }
        }
        log.info("{}, {}",topics.get(chatId).getTopic(), redisMessageListener.getConnectionFactory());
    }
    @Transactional
    public ResponseEntity<String> deleteChat(Long chatId){
        Person person = authService.findById(SecurityUtil.getCurrentUserId());
        Chat chat = findById(chatId);
        chat.deletePerson(person);
        if (chat.getFirstPerson() == null && chat.getSecondPerson() == null){
            chatRepository.delete(chat);
            opsHashMessageRoom.delete("MESSAGE_ROOM", chatId.toString());
            topics.remove(chatId.toString(), getTopic(chatId.toString()));
        }
        return ResponseEntity.ok().body("ok");
    }
    public ChannelTopic getTopic(String roomId) {
        return topics.get(roomId);
    }
    public Chat findById(Long chatId){
        return chatRepository.findById(chatId).orElseThrow(() -> new CustomException(ErrorCode.CHAT_NOT_FOUND));
    }
}
