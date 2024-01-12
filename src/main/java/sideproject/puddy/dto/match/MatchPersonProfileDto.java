package sideproject.puddy.dto.match;

import lombok.AllArgsConstructor;
import lombok.Data;
import sideproject.puddy.dto.dog.response.DogProfileDto;

@Data
@AllArgsConstructor
public class MatchPersonProfileDto {
    private Long id;
    private boolean gender;
    private DogProfileDto dog;
}
