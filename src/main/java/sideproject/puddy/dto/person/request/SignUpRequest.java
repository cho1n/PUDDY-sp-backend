package sideproject.puddy.dto.person.request;

import lombok.Data;

import java.util.Date;

@Data
public class SignUpRequest {
    private String login;
    private String password;
    private String gender;
    private String mainAddress;
    private String subAddress;
    private Date birth;
}
