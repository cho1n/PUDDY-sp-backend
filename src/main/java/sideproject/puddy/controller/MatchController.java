package sideproject.puddy.controller;

import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sideproject.puddy.dto.match.MatchSearchResponse;
import sideproject.puddy.dto.match.RandomDogDetailListResponse;
import sideproject.puddy.dto.match.RandomDogDetailResponse;
import sideproject.puddy.dto.tag.TagDto;
import sideproject.puddy.dto.tag.TagListDto;
import sideproject.puddy.service.MatchService;
import sideproject.puddy.service.NotificationService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MatchController {
    private final MatchService matchService;
    private final NotificationService notificationService;

    @GetMapping("/random")
    public ResponseEntity<RandomDogDetailListResponse> findMatches(
            @Nullable @RequestParam(required = false) String type,
            @RequestParam(required = false) Boolean neuter,
            @RequestParam(required = false) List<TagDto> tags
    ){
        log.info("type: {}, neuter: {}, tags: {}", type, neuter, tags);
        // type, neuter, tag 파라미터를 사용하여 매칭 도그를 찾는 로직
        return ResponseEntity.ok(matchService.getMatchingByDog(type, neuter, tags));
    }



    @GetMapping("/random/{personId}")
    public ResponseEntity<RandomDogDetailResponse> findMatchDetailByPerson(@PathVariable Long personId){
        RandomDogDetailResponse matchSearchResponse = matchService.getShowingByMatchDetail(personId);
        return ResponseEntity.ok(matchSearchResponse);
    }

    @GetMapping("/match")
    public ResponseEntity<MatchSearchResponse> findWhoPostLike(){
        MatchSearchResponse matchSearchResponse = matchService.getPersonProfileWhoPostLike();
        return ResponseEntity.ok(matchSearchResponse);
    }
    @GetMapping("/matched")
    public ResponseEntity<MatchSearchResponse> findWhoPostMatched(){
        MatchSearchResponse matchSearchResponse = matchService.getSuccessMatched();
        return ResponseEntity.ok(matchSearchResponse);
    }

    @PostMapping("/match/{personId}")
    public ResponseEntity<String> likePost(@PathVariable Long personId) {
        matchService.likeProfile(personId);
        notificationService.notifyAlert(personId);
        return ResponseEntity.ok("Like post successful");
    }
}
