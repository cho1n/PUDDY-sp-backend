package sideproject.puddy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sideproject.puddy.dto.match.response.MatchPersonProfileDto;
import sideproject.puddy.dto.match.response.MatchSearchResponse;
import sideproject.puddy.model.Match;
import sideproject.puddy.model.Person;
import sideproject.puddy.repository.MatchRepository;
import sideproject.puddy.repository.PersonRepository;
import sideproject.puddy.dto.dog.response.DogDetailResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MatchService {

    private final MatchRepository matchRepository;
    private final PersonRepository personRepository;

    @Autowired
    public MatchService(MatchRepository matchRepository, PersonRepository personRepository) {
        this.matchRepository = matchRepository;
        this.personRepository = personRepository;
    }

    public MatchSearchResponse findMatches(Long personId) {
        Person sender = personRepository.findById(personId)
                .orElseThrow(() -> new RuntimeException("Person not found"));

        List<Match> matches = matchRepository.findBySenderOrReceiver(sender, sender);

        List<MatchPersonProfileDto> matchPersonProfiles = matches.stream()
                .map(match -> {
                    Person matchedPerson = match.getSender().equals(sender) ? match.getReceiver() : match.getSender();
                    return new MatchPersonProfileDto(
                            matchedPerson.getId(),
                            matchedPerson.isGender(),
                            mapDogsToDto(matchedPerson.getDogs())
                    );
                })
                .collect(Collectors.toList());

        return new MatchSearchResponse(matchPersonProfiles);
    }

    public void createMatch(Long senderId, Long receiverId) {
        Person sender = personRepository.findById(senderId)
                .orElseThrow(() -> new RuntimeException("Sender not found"));

        Person receiver = personRepository.findById(receiverId)
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        // 이미 매치된 경우에는 중복 생성하지 않도록 체크
        if (!matchRepository.existsBySenderAndReceiver(sender, receiver)) {
            Match match = new Match(sender, receiver);
            matchRepository.save(match);
        }
    }

    private List<MatchPersonProfileDto.DogDto> mapDogsToDto(List<Dog> dogs) {
        return dogs.stream()
                .map(dog -> new MatchPersonProfileDto.DogDto(dog.getImage(), dog.getName()))
                .collect(Collectors.toList());
    }
}
