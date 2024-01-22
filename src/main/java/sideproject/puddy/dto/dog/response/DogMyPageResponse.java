package sideproject.puddy.dto.dog.response;

import lombok.Builder;

@Builder
public record DogMyPageResponse (
        Long id,

        String name,
        Integer age,
        String type,
        boolean main,
        String image
) {
    public static DogMyPageResponse of (
            Long id, String name, Integer age, String type, boolean main, String image
    ) {
        return DogMyPageResponse.builder()
                .id(id)
                .name(name)
                .age(age)
                .type(type)
                .main(main)
                .image(image)
                .build();
    }
}
