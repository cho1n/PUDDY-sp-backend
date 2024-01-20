    package sideproject.puddy.exception;

    import lombok.AllArgsConstructor;
    import lombok.Getter;
    import org.springframework.http.HttpStatus;

    @AllArgsConstructor
    @Getter
    public enum ErrorCode {
        WRONG_PW_INPUT_VALUE(HttpStatus.BAD_REQUEST, "비밀번호 양식이 올바르지 않습니다"),
        WRONG_LOGIN_INPUT(HttpStatus.NOT_FOUND, "아이디 혹은 비밀번호가 올바르지 않습니다"),
        INVALID_ACCESS_TOKEN(HttpStatus.BAD_REQUEST, "AccessToken이 유효하지 않습니다."),
        INVALID_REFRESH_TOKEN(HttpStatus.BAD_REQUEST, "RefreshToken이 유효하지 않습니다."),
        DOG_NUM_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 반려동물입니다."),
        USER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 유저입니다."),
        POST_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 게시물입니다."),
        COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 댓글입니다."),
        TRAIL_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 산책로입니다."),
        REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 리뷰입니다."),
        TAG_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 태그입니다"),
        ;

        private final HttpStatus httpStatus;
        private final String message;
    }
