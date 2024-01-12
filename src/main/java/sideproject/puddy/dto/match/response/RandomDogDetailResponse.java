package sideproject.puddy.dto.match.response;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RandomDogDetailResponse {
    private boolean gender;
    private Integer age;
    private String mainAddress;
    private RandomDogProfileDto dog;
}
