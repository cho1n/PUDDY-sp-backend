package sideproject.puddy.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sideproject.puddy.dto.trailreview.request.PostTrailReviewRequest;
import sideproject.puddy.dto.trailreview.response.TrailReviewListResponse;
import sideproject.puddy.dto.trailreview.response.TrailReviewResponse;
import sideproject.puddy.model.Dog;
import sideproject.puddy.model.Person;
import sideproject.puddy.model.Trail;
import sideproject.puddy.model.TrailReview;
import sideproject.puddy.repository.TrailReviewRepository;
import sideproject.puddy.security.util.SecurityUtil;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TrailReviewService {
    private final TrailReviewRepository trailReviewRepository;
    private final TrailService trailService;
    private final AuthService authService;
    public TrailReviewListResponse findAllReviewByTrail(Long trailId, PageRequest pageRequest){
        Trail trail = trailService.findById(trailId);
        List<TrailReviewResponse> trailReviewResponses = trailReviewRepository.findAllByTrail(trail, pageRequest).stream().map(trailReview ->
                new TrailReviewResponse(trailReview.getId(), trailReview.getStar(), trailReview.getContent(),
                        trailReview.getReviewer().getGender(),
                        getMainDog(trailReview.getReviewer().getDogs()).getName(),
                        getMainDog(trailReview.getReviewer().getDogs()).getImage(), trailReview.getCreatedAt())).toList();
        return new TrailReviewListResponse((long) trailReviewResponses.size(), trailReviewResponses);
    }
    public ResponseEntity<String> saveTrailReview(Long trailId, PostTrailReviewRequest request){
        Trail trail = trailService.findById(trailId);
        Person person = authService.findById(SecurityUtil.getCurrentUserId());
        trailReviewRepository.save(new TrailReview(request.getStar(), request.getContent(), person, trail));
        return ResponseEntity.ok().body("ok");
    }
    public ResponseEntity<String> deleteTrailReview(Long trailId, Long id){
        Trail trail = trailService.findById(trailId);
        TrailReview trailReview = findById(id);
        trailReviewRepository.delete(trailReview);
        return ResponseEntity.ok().body("ok");
    }
    public TrailReview findById(Long id){
        return trailReviewRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 리뷰입니다"));
    }
    //대표 강아지 선택 임시코드 (dog service에서 건드려야할듯)
    public Dog getMainDog(List<Dog> dogs){
        return dogs.get(0);
    }
}
