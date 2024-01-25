package sideproject.puddy.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableRedisRepositories(basePackages = "sideproject.puddy.repository.redis")
public class RedisConfig {
    @Value("${spring.data.redis.host}")
    private String redisHost;
    @Value("${spring.data.redis.port}")
    private int redisPort;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(new RedisStandaloneConfiguration(redisHost, redisPort));
    }
    @Bean
    public RedisMessageListenerContainer redisMessageListener(RedisConnectionFactory connectionFactory) {		// 1.
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);		// 2.

        return container;
    }
    // Redis 데이터베이스와의 상호작용을 위한 RedisTemplate 을 설정. JSON 형식으로 담기 위해 직렬화
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());        // Key Serializer
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(String.class));      // Value Serializer

        return redisTemplate;
    }
    // Redis 에 메시지 내역을 저장하기 위한 RedisTemplate 을 설정
    @Bean
    public RedisTemplate<String, ?> redisTemplateMessage(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, ?> redisTemplateMessage = new RedisTemplate<>();
        redisTemplateMessage.setConnectionFactory(connectionFactory);
        redisTemplateMessage.setKeySerializer(new StringRedisSerializer());        // Key Serializer
        redisTemplateMessage.setValueSerializer(new Jackson2JsonRedisSerializer<>(String.class));      // Value Serializer

        return redisTemplateMessage;
    }
}