package sideproject.puddy.security.util;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import sideproject.puddy.exception.CustomException;
import sideproject.puddy.exception.ErrorCode;

@NoArgsConstructor
public class SecurityUtil {
    public static Long getCurrentUserId(){
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null){
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }
        return Long.parseLong(authentication.getName());
    }
}
