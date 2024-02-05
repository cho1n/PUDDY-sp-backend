package sideproject.puddy.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;
import sideproject.puddy.dto.message.MessageDto;

@Service
@RequiredArgsConstructor
public class RedisPublisher {

    private final RedisTemplate<String, Object> redisTemplate;

    public void publish(ChannelTopic topic, MessageDto message) {
        redisTemplate.convertAndSend(topic.getTopic(), message);
    }
}
