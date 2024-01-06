package sideproject.puddy.dto.trailreview.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class TrailReviewResponse {
    private Long id;
    private Integer star;
    private String content;
    private String personGender;
    private String dogName;
    private String dogImage;
    private LocalDate createdAt;
}
