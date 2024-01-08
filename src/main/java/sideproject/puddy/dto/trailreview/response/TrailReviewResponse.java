package sideproject.puddy.dto.trailreview.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import sideproject.puddy.dto.person.response.PersonProfileDto;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class TrailReviewResponse {
    private Long id;
    private PersonProfileDto reviewer;
    private Integer star;
    private String content;
    private LocalDate createdAt;
    private boolean isMine;
}
