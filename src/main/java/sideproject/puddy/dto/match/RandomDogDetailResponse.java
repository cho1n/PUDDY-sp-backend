package sideproject.puddy.dto.match;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RandomDogDetailResponse {
    private Long id;
    private String login;
    private boolean gender;
    private Integer age;
    private String mainAddress;
    private RandomDogProfileDto dog;
}
