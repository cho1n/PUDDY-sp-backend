package sideproject.puddy.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sideproject.puddy.dto.match.response.MatchPersonProfileDto;
import sideproject.puddy.dto.match.response.MatchSearchResponse;
import sideproject.puddy.model.Dog;
import sideproject.puddy.model.Match;
import sideproject.puddy.model.Person;
import sideproject.puddy.repository.MatchRepository;
import sideproject.puddy.repository.PersonRepository;
import sideproject.puddy.security.util.SecurityUtil;
import org.springframework.data.domain.Page;


import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;
    private final PersonRepository personRepository;
    private final AuthService authService;

    // 위치, 매칭 여부 -> (성별, 나이, 반려견 정보)
    public MatchSearchResponse getMatchingByDog(int pageNum) {
        Person currentUser = authService.findById(SecurityUtil.getCurrentUserId()) ;

        Page<MatchPersonProfileDto> matchPersonProfiles = matchRepository.findNearPersonNotMatched(
                        SecurityUtil.getCurrentUserId(),
                        !currentUser.isGender(),
                        currentUser.getLongitude(),
                        currentUser.getLatitude(),
                        PageRequest.of(pageNum, 1)
                )
                .map(person -> new MatchPersonProfileDto(
                        person.getId(),
                        person.isGender(),
                        mapDogsToDto(person.getDogs())
                ));

        return new MatchSearchResponse(matchPersonProfiles);
    }

    @Transactional
    public void likeProfile(Long receiverId) {
        Person sender = authService.findById(SecurityUtil.getCurrentUserId()) ;
        Person receiver = personRepository.findById(receiverId)
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        // 이미 매치된 경우에는 중복 생성하지 않도록 체크
        if (!matchRepository.existsBySenderAndReceiver(sender, receiver))
            matchRepository.save(new Match(sender, receiver));
    }

    private List<DogProfileDto> mapDogsToDto(List<Dog> dogs) {
        return dogs.stream()
                .map(dog -> new DogProfileDto(dog.getImage(), dog.getName()))
                .collect(Collectors.toList());
    }
}
