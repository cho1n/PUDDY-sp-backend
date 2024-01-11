package sideproject.puddy.dto.match.response;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;
import sideproject.puddy.dto.dog.response.DogDetailResponse;

@Data
@AllArgsConstructor
public class RandomDogDetailResponse {
    private boolean gender;
    private int age;
    private String mainAddress;
    private DogDetailResponse dogs;

}
