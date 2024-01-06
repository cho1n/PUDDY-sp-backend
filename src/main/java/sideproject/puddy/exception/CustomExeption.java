package sideproject.puddy.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CustomExeption extends RuntimeException {
    ErrorCode errorCode;
}
