package sideproject.puddy.dto.match;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;


@Data
@AllArgsConstructor
public class MatchSearchResponse {
    private List<MatchPersonProfileDto> matches;
}
