package sideproject.puddy.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;
import sideproject.puddy.dto.message.MessageDto;
import sideproject.puddy.dto.message.request.PostMessageRequest;
import sideproject.puddy.model.ChatMessage;
import sideproject.puddy.model.Person;
import sideproject.puddy.redis.RedisPublisher;
import sideproject.puddy.repository.MessageRepository;
import sideproject.puddy.security.util.SecurityUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageService {
    private final RedisTemplate<String, MessageDto> redisTemplateMessage;
    private final ChatService chatService;
    private final MessageRepository messageRepository;
    private final RedisPublisher redisPublisher;
    private final AuthService authService;
    public void onMessage(PostMessageRequest postMessageRequest){
        Person person = authService.findById(SecurityUtil.getCurrentUserId());
        ChatMessage chatMessage = new ChatMessage(chatService.findById(postMessageRequest.getChatId()), person, postMessageRequest.getContent());
        MessageDto messageDto = new MessageDto(chatMessage);
        messageRepository.save(chatMessage);
        redisTemplateMessage.setValueSerializer(new Jackson2JsonRedisSerializer<>(Message.class));
        redisTemplateMessage.opsForList().rightPush(postMessageRequest.getChatId().toString(), messageDto);
        redisTemplateMessage.expire(postMessageRequest.getChatId().toString(), 1, TimeUnit.DAYS);
        redisPublisher.publish(chatService.getTopic(postMessageRequest.getChatId().toString()), messageDto);
    }
    // 6. 대화 조회 - Redis & DB
    public List<MessageDto> loadMessage(Long chatId) {
        List<MessageDto> messageList = new ArrayList<>();
        Long listSize = redisTemplateMessage.opsForList().size(chatId.toString());

        List<MessageDto> redisMessageList = redisTemplateMessage.opsForList().range(chatId.toString(), 0, listSize-1);

        if (redisMessageList == null || redisMessageList.isEmpty()) {
            // 5.
            List<ChatMessage> dbMessageList = messageRepository.findAllByChatId(chatId);

            for (ChatMessage message : dbMessageList) {
                MessageDto messageDto = new MessageDto(message);
                messageList.add(messageDto);
                redisTemplateMessage.setValueSerializer(new Jackson2JsonRedisSerializer<>(Message.class));      // 직렬화
                redisTemplateMessage.opsForList().rightPush(chatId.toString(), messageDto);                     // redis 저장
            }
        } else {
            // 7.
            messageList.addAll(redisMessageList);
        }

        return messageList;
    }
}
