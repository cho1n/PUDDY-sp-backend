package sideproject.puddy.dto.person.response;


import lombok.Builder;

import java.time.LocalDate;

@Builder
public record PersonInfoResponse (
    String login,
    LocalDate birth,
    String mainAddress,
    String subAddress,
    boolean gender
) {
    public static PersonInfoResponse of (
            String login, LocalDate birth, String mainAddress, String subAddress, boolean gender
    ) {
        return PersonInfoResponse.builder()
                .login(login)
                .birth(birth)
                .mainAddress(mainAddress)
                .subAddress(subAddress)
                .gender(gender)
                .build();
    }
}
