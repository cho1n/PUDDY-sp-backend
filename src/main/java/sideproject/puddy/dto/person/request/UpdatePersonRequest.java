package sideproject.puddy.dto.person.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdatePersonRequest {
    private String password;
    private LocalDate birth;
    private String mainAddress;
    private String subAddress;
    private boolean gender;
}
