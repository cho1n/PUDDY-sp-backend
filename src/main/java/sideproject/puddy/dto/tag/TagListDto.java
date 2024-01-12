package sideproject.puddy.dto.tag;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TagListDto {
    private List<TagDto> dogTags;
}
