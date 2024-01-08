package sideproject.puddy.dto.match.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import sideproject.puddy.dto.match.response.MatchPersonProfileDto;

import java.util.List;

@Data
@AllArgsConstructor
public class MatchSearchResponse {
    private List<MatchPersonProfileDto> matchPersonProfileDto;
}
