package sideproject.puddy.dto.match.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;


@Data
@AllArgsConstructor
public class MatchSearchResponse {
    private Page<MatchPersonProfileDto> persons; // People인거 아는데 그냥 해놓은겁니다.
}
