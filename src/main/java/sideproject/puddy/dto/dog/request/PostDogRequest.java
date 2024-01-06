package sideproject.puddy.dto.dog.request;

import lombok.Data;
import sideproject.puddy.dto.tag.TagDto;

import java.util.List;

@Data
public class PostDogRequest {
    private String image;
    private Long registerNum;
    private String name;
    private String birth;
    private String type;
    private String gender;
    private boolean neuter;
    private List<TagDto> tags;
}
