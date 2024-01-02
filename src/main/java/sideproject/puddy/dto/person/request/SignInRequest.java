package sideproject.puddy.dto.person.request;

import lombok.Data;

@Data
public class SignInRequest {
    private String login;
    private String password;
}
