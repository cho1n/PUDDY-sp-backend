package sideproject.puddy.dto.dog.response;

import lombok.Builder;

@Builder
public record DogMyPageResponse (

        String name,
        Integer age,
        String type,
        boolean main,
        String image
) {
    public static DogMyPageResponse of (
            String name, Integer age, String type, boolean main, String image
    ) {
        return DogMyPageResponse.builder()
                .name(name)
                .age(age)
                .type(type)
                .main(main)
                .image(image)
                .build();
    }
}
