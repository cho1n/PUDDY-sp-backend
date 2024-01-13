package sideproject.puddy.dto.match;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class RandomDogDetailListResponse {
    List<RandomDogDetailResponse> pets;
}
