package sideproject.puddy.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sideproject.puddy.dto.dog.request.PostDogRequest;
import sideproject.puddy.dto.dog.request.UpdateDogRequest;
import sideproject.puddy.dto.dog.response.DogDetailResponse;
import sideproject.puddy.dto.dog.response.DogMyPageResponse;
import sideproject.puddy.dto.dog.response.DogProfileDto;
import sideproject.puddy.dto.tag.TagDto;
import sideproject.puddy.exception.CustomException;
import sideproject.puddy.exception.ErrorCode;
import sideproject.puddy.model.Dog;
import sideproject.puddy.model.Person;
import sideproject.puddy.repository.DogRepository;
import sideproject.puddy.security.util.SecurityUtil;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class DogService {
    private final DogRepository dogRepository;
    private final DogTypeService dogTypeService;
    private final DogTagMapService dogTagMapService;
    private final AuthService authService;
    private final DogTransactionalService dogTransactionalService;
    public ResponseEntity<String> saveDogBySignUp(Long personId, PostDogRequest request){
        Person person = authService.findById(personId);
        dogTransactionalService.save(person, request);
        return ResponseEntity.ok().body("ok");
    }
    public ResponseEntity<String> saveDog(PostDogRequest request){
        Person person = authService.findById(SecurityUtil.getCurrentUserId());
        dogTransactionalService.save(person, request);
        return ResponseEntity.ok().body("ok");
    }
    @Transactional
    public ResponseEntity<String> updateDog(Long id, UpdateDogRequest request){
        Person person = authService.findById(SecurityUtil.getCurrentUserId());
        Dog dog = dogRepository.findByPersonAndId(person, id)
                .orElseThrow(() -> new CustomException(ErrorCode.DOG_NUM_NOT_FOUND));
        dog.updateDog(request.getImage(), dogTypeService.findByContent(request.getType()), request.isGender(), request.isNeuter());
        dogTagMapService.deleteAllTags(dog);
        dogTagMapService.saveAllTags(request.getTags(), dog);
        return ResponseEntity.ok().body("ok");
    }
    @Transactional
    public ResponseEntity<String> deleteDog(Long id){
        Person person = authService.findById(SecurityUtil.getCurrentUserId());
        if (person.getDogs().stream().count() <= 1){
            throw new CustomException(ErrorCode.DOG_NUM_NOT_FOUND);
        }
        Dog dog = dogRepository.findByPersonAndId(person, id)
                .orElseThrow(() -> new CustomException(ErrorCode.DOG_NUM_NOT_FOUND));
        if (dog.isMain()) {
            dogRepository.deleteById(dog.getId());
            Dog firstDog = dogRepository.findFirstByPerson(person)
                    .orElseThrow(() -> new CustomException(ErrorCode.DOG_NUM_NOT_FOUND));
            log.info("firstDog: {}", firstDog);
            firstDog.updateMainDog(true);
        } else {
            dogRepository.deleteById(dog.getId());
        }
        return ResponseEntity.ok().body("ok");
    }
    @Transactional
    public ResponseEntity<String> updateMainDog(Long id){
        Person person = authService.findById(SecurityUtil.getCurrentUserId());
        Dog dog = dogRepository.findByPersonAndId(person, id)
                .orElseThrow(() -> new CustomException(ErrorCode.DOG_NUM_NOT_FOUND));
        dogRepository.findAllByPerson(person).forEach(dogs -> dogs.updateMainDog(false));
        dog.updateMainDog(true);
        return ResponseEntity.ok().body("ok");
    }
    public DogDetailResponse findDog(Long id) {
        Person person = authService.findById(SecurityUtil.getCurrentUserId());
        Dog dog = dogRepository.findByPersonAndId(person, id)
                .orElseThrow(() -> new CustomException(ErrorCode.DOG_NUM_NOT_FOUND));
        List<TagDto> tags = dog.getDogTagMaps().stream().map(dogTagMap -> new TagDto(dogTagMap.getDogTag().getContent())).toList();
        return new DogDetailResponse(dog.getImage(), dog.isGender(), dog.getDogType().getContent(), dog.isNeuter(), tags);
    }

    public DogProfileDto findProfileByPersonAndMain(Person person){
        if (!dogRepository.existsByPersonAndMain(person, true)){
            return null;
        }
        Dog dog = dogRepository.findByPersonAndMain(person, true).orElseThrow(() -> new CustomException(ErrorCode.DOG_NUM_NOT_FOUND));
        return new DogProfileDto(dog.getName(), dog.getImage());
    }
    public Dog findByPersonAndMain(Person person){
        if (!dogRepository.existsByPersonAndMain(person, true)){
            return null;
        }
        return dogRepository.findByPersonAndMain(person, true).orElseThrow(() -> new CustomException(ErrorCode.DOG_NUM_NOT_FOUND));
    }
    public List<DogMyPageResponse> getDogMyPage(Person person) {
        return dogRepository.findAllByPerson(person).stream()
                .map(dog -> DogMyPageResponse.of(
                        dog.getId(),
                        dog.getName(),
                        calcAge(dog.getBirth().getYear()),
                        dog.getDogType().getContent(),
                        dog.isMain(),
                        dog.getImage()
                ))
                .toList();
    }
    public Integer calcAge(Integer year) {
        return LocalDate.now().getYear() - year + 1;
    }
}
