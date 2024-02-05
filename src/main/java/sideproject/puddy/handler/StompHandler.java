package sideproject.puddy.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import sideproject.puddy.security.jwt.JwtTokenProvider;

@RequiredArgsConstructor
@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
@Slf4j
public class StompHandler implements ChannelInterceptor {
    private final JwtTokenProvider jwtTokenProvider;
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel){
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        if (accessor.getCommand() == StompCommand.CONNECT || accessor.getCommand() == StompCommand.SEND || accessor.getCommand() == StompCommand.SUBSCRIBE){
            String token = accessor.getFirstNativeHeader("Authorization").substring(7);
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            log.info("{}, {}", message, authentication);
            jwtTokenProvider.validateToken(token);
        }
        return message;
    }
}
