package sideproject.puddy.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExeptionHandler {
    @ExceptionHandler(CustomExeption.class)
    protected ResponseEntity<ErrorResponseEntity> handleCustomExeption(CustomExeption e) {
        return ErrorResponseEntity.toResponseEntity(e.getErrorCode());
    }
}

