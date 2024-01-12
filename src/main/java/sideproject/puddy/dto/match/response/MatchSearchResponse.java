package sideproject.puddy.dto.match.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;


@Data
@AllArgsConstructor
public class MatchSearchResponse {
    private List<MatchPersonProfileDto> matches;
}
