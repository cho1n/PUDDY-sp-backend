package sideproject.puddy.dto.match.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import sideproject.puddy.dto.person.response.PersonProfileDto;
import sideproject.puddy.dto.dog.response.DogProfileDto;
import java.util.List;

public class MatchPersonProfileDto {
    private Long id;
    private boolean gender;
    private List<DogProfileDto> dog;

}
