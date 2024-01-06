package sideproject.puddy.dto.person.request;

import lombok.Data;

import java.util.Date;

@Data
public class SignUpRequest {
    private String login;
    private String password;
    private boolean gender;
    private String mainAddress;
    private String subAddress;
    private String birth;
}
