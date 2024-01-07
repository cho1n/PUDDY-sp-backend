package sideproject.puddy.dto.person.response;

import lombok.Builder;
import sideproject.puddy.dto.dog.response.DogMyPageResponse;

import java.util.List;

@Builder
public record MyPageInfoResponse (

        boolean gender,
        List<DogMyPageResponse> dogs
) {
    public static MyPageInfoResponse of (
            List<DogMyPageResponse> dogs, boolean gender
    ) {
        return MyPageInfoResponse.builder()
                .dogs(dogs)
                .gender(gender)
                .build();
    }
}
