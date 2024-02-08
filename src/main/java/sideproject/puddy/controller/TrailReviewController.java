package sideproject.puddy.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sideproject.puddy.dto.trailreview.request.PostTrailReviewRequest;
import sideproject.puddy.dto.trailreview.response.TrailReviewListResponse;
import sideproject.puddy.service.TrailReviewService;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/trail/{trailId}")
public class TrailReviewController {
    private final TrailReviewService trailReviewService;
    @PostMapping("/review")
    public ResponseEntity<String> postTrailReview(@PathVariable Long trailId, @RequestBody PostTrailReviewRequest request){
        return trailReviewService.saveTrailReview(trailId, request);
    }
    @GetMapping("/review")
    public TrailReviewListResponse getTrailReviewList(@PathVariable Long trailId, @RequestParam int pageNum){
        PageRequest pageRequest = PageRequest.of(pageNum - 1, 6, Sort.by("id").descending());
        return trailReviewService.findAllReviewByTrail(trailId, pageRequest);
    }
    @DeleteMapping("/review/{reviewId}")
    public ResponseEntity<String> deleteTrailReview(@PathVariable Long trailId, @PathVariable Long reviewId){
        return trailReviewService.deleteTrailReview(trailId, reviewId);
    }
}
