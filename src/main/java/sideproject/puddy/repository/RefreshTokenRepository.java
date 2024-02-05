package sideproject.puddy.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepository {
    private final RedisTemplate<String, Object> redisTemplate;
    private static final String KEY = "refreshToken";
    private String genKey(Long personId){
        return KEY + ":" + personId;
    }
    public void save(String refreshToken, Long personId){
        String key = genKey(personId);
        redisTemplate.opsForValue().set(key, refreshToken);
        redisTemplate.expire(key, 3, TimeUnit.HOURS);
    }
    public String findByPersonId(Long personId){
        String key = genKey(personId);
        return (String) redisTemplate.opsForValue().get(key);
    }
    public void delete(Long personId){
        String key = genKey(personId);
        redisTemplate.delete(key);
    }
    public boolean existsByPersonId(Long personId) {
        String key = genKey(personId);
        return redisTemplate.hasKey(key);
    }
    public void update(String newRefreshToken, Long personId){
        String key = genKey(personId);
        redisTemplate.opsForValue().set(key, newRefreshToken);
        redisTemplate.expire(key, 3, TimeUnit.HOURS);
    }
}
