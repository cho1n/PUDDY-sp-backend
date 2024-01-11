package sideproject.puddy.dto.match.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;
import sideproject.puddy.dto.match.response.MatchPersonProfileDto;

import java.util.List;

@Data
@AllArgsConstructor
public class MatchSearchResponse {
    private Page<MatchPersonProfileDto> persons; // People인거 아는데 그냥 해놓은겁니다.
}
