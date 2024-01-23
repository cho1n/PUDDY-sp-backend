package sideproject.puddy.model;

import org.springframework.data.annotation.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash(value = "refreshToken", timeToLive = 60 * 180 * 1000L)
public class RefreshToken {
    @Id
    private String token;
    private Long personId;

    public RefreshToken(String token, Long personId) {
        this.token = token;
        this.personId = personId;
    }
    public void updateToken(String token){
        this.token = token;
    }
}
