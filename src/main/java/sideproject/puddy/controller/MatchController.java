package sideproject.puddy.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sideproject.puddy.dto.match.response.MatchSearchResponse;
import sideproject.puddy.dto.person.response.PersonInfoResponse;
import sideproject.puddy.model.Match;
import org.springframework.data.domain.PageRequest;
import sideproject.puddy.service.MatchService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/match")
public class MatchController {
    private final MatchService matchService;

    @GetMapping
    public ResponseEntity<MatchSearchResponse> findMatches(@RequestParam int pageNum){
        MatchSearchResponse matchSearchResponse = matchService.getMatchingByDog(pageNum);
        return ResponseEntity.ok(matchSearchResponse);
    }

    @PostMapping("/{personId}")
    public ResponseEntity<String> likePost(@PathVariable Long personId) {
        matchService.likeProfile(personId);
        return ResponseEntity.ok("Like post successful");
    }
}
