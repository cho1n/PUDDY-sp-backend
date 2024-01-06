package sideproject.puddy.dto.dog.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import sideproject.puddy.dto.tag.TagDto;

import java.util.List;

@Data
public class UpdateDogRequest {
    private String image;
    private String type;
    private boolean gender;
    private boolean neuter;
    private List<TagDto> tags;
}
