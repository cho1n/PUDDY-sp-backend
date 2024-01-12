package sideproject.puddy.dto.match.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class RandomDogDetailListResponse {
    List<RandomDogDetailResponse> pets;
}
