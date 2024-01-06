package sideproject.puddy.dto.trailreview.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TrailReviewListResponse {
    private Long count;
    private List<TrailReviewResponse> trailReviews;
}
