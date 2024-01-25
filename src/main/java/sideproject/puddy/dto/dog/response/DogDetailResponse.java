package sideproject.puddy.dto.dog.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import sideproject.puddy.dto.tag.TagDto;

import java.util.List;

@Data
@AllArgsConstructor
public class DogDetailResponse {
    private String name;
    private Long registerNum;
    private String image;
    private boolean gender;
    private String type;
    private boolean neuter;
    private List<TagDto> tags;
}
