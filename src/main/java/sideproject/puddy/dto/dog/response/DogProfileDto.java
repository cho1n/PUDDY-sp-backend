package sideproject.puddy.dto.dog.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DogProfileDto {
    private String name;
    private String image;
}
