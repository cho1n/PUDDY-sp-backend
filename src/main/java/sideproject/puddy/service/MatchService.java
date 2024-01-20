package sideproject.puddy.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sideproject.puddy.dto.dog.response.DogProfileDto;
import sideproject.puddy.dto.match.*;
import sideproject.puddy.dto.tag.TagDto;
import sideproject.puddy.exception.CustomException;
import sideproject.puddy.exception.ErrorCode;
import sideproject.puddy.model.*;
import sideproject.puddy.repository.MatchRepository;
import sideproject.puddy.repository.PersonRepository;
import sideproject.puddy.security.util.SecurityUtil;


import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;
    private final PersonRepository personRepository;
    private final AuthService authService;
    private final DogService dogService;


    // 위치, 매칭 여부 -> (성별, 나이, 반려견 정보)
    public RandomDogDetailListResponse getMatchingByDog(int pageNum) {
        Person currentUser = authService.findById(SecurityUtil.getCurrentUserId());

        List<RandomDogDetailResponse> dogs = matchRepository.findNearPersonNotMatched(
                        SecurityUtil.getCurrentUserId(),
                        !currentUser.isGender(),
                        currentUser.getLongitude(),
                        currentUser.getLatitude(),
                        PageRequest.of(pageNum, 1)
                )
                .map(person -> {
                    // 각 상대방의 main 강아지 탐색
                    Dog mainDog = dogService.findByPersonAndMain(person);

                    RandomDogProfileDto randomDogProfileDto = new RandomDogProfileDto(
                            mainDog.getName(),
                            mainDog.getImage(),
                            mainDog.getDogType().getContent(),
                            calculateAge(mainDog.getBirth()),
                            mapTagsToDto(mainDog.getDogTagMaps())
                    );

                    return new RandomDogDetailResponse(
                            person.getId(),
                            person.getLogin(),
                            person.isGender(),
                            calculateAge(person.getBirth()),
                            person.getMainAddress(),
                            randomDogProfileDto
                    );
                })
                .toList();
        return new RandomDogDetailListResponse(dogs);
    }

    public MatchSearchResponse getPersonProfileWhoPostLike() {
        Person currentUser = authService.findById(SecurityUtil.getCurrentUserId());
        List<Match> matches = matchRepository.findByReceiverId(currentUser.getId());

        List<MatchPersonProfileDto> matchPersonProfileDtoList = matches
                .stream()
                .map(match -> {
                    Long personId = match.getId();
                    Person person = authService.findById(personId);
                    Dog mainDog = dogService.findByPersonAndMain(person);

                    DogProfileDto dogProfileDto = new DogProfileDto(
                            mainDog.getImage(),
                            mainDog.getName()
                    );
                    return new MatchPersonProfileDto(
                            personId,
                            mainDog.isGender(),
                            dogProfileDto
                    );
                })
                .collect(Collectors.toList());

        return new MatchSearchResponse(matchPersonProfileDtoList);
    }

    public MatchSearchResponse getSuccessMatched() {
        Long currentUserId = SecurityUtil.getCurrentUserId();
        List<Match> matches = matchRepository.findByReceiverIdOrSenderId(currentUserId, currentUserId);

        List<MatchPersonProfileDto> result = new ArrayList<>();

        for (Match sending : matches) {
            Person sendingSender = sending.getSender();
            Person sendingReceiver = sending.getReceiver();
            for (Match received : matches) {
                Person receivedSender = received.getSender();
                Person receivedReceiver = received.getReceiver();

                if (sendingSender.equals(receivedReceiver) && sendingReceiver.equals(receivedSender) &&
                        !sendingReceiver.getId().equals(currentUserId)) {
                    result.add(new MatchPersonProfileDto(
                            receivedSender.getId(),
                            receivedSender.isGender(),
                            new DogProfileDto(
                                    dogService.findByPersonAndMain(receivedSender).getImage(),
                                    dogService.findByPersonAndMain(receivedSender).getName()
                            )
                    ));
                }
            }
        }
        return new MatchSearchResponse(result);
    }


    private int calculateAge(LocalDate birthDate) {
        LocalDate currentDate = LocalDate.now();
        return Period.between(birthDate, currentDate).getYears();
    }

        @Transactional
    public void likeProfile(Long receiverId) {
        Person sender = authService.findById(SecurityUtil.getCurrentUserId());
        Person receiver = personRepository.findById(receiverId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        // 이미 매치된 경우에는 중복 생성하지 않도록 체크
        if (!matchRepository.existsBySenderAndReceiver(sender, receiver))
            matchRepository.save(new Match(sender, receiver));
    }

    private List<TagDto> mapTagsToDto(List<DogTagMap> dogTagMaps) {
        return dogTagMaps.stream()
                .map(dogTagMap -> new TagDto(dogTagMap.getDogTag().getContent()))
                .collect(Collectors.toList());
    }
    public RandomDogDetailResponse getShowingByMatchDetail(Long personId) {
        Person person = personRepository.findById(personId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        Dog mainDog = dogService.findByPersonAndMain(person);
        return new RandomDogDetailResponse(
                person.getId(),
                person.getLogin(),
                person.isGender(),
                calculateAge(person.getBirth()),
                person.getMainAddress(),
                new RandomDogProfileDto(
                        mainDog.getName(),
                        mainDog.getImage(),
                        mainDog.getDogType().getContent(),
                        calculateAge(mainDog.getBirth()),
                        mapTagsToDto(mainDog.getDogTagMaps())
                )
        );
    }

}