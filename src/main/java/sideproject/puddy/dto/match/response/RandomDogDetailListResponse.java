package sideproject.puddy.dto.match.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import sideproject.puddy.dto.match.response.RandomDogDetailResponse;
import java.util.List;

@Data
@AllArgsConstructor
public class RandomDogDetailListResponse {
    List<RandomDogDetailResponse> dogs;
}
