package sideproject.puddy.dto.person.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import sideproject.puddy.dto.dog.response.DogProfileDto;

@Data
@AllArgsConstructor
public class PersonProfileDto {
    private boolean gender;
    private DogProfileDto dog;
}
