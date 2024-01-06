package sideproject.puddy.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sideproject.puddy.dto.dog.request.PostDogRequest;
import sideproject.puddy.dto.dog.request.UpdateDogRequest;
import sideproject.puddy.dto.dog.response.DogDetailResponse;
import sideproject.puddy.dto.tag.TagDto;
import sideproject.puddy.exception.CustomException;
import sideproject.puddy.exception.ErrorCode;
import sideproject.puddy.model.Dog;
import sideproject.puddy.model.DogTagMap;
import sideproject.puddy.model.Person;
import sideproject.puddy.repository.DogRepository;
import sideproject.puddy.repository.DogTagMapRepository;
import sideproject.puddy.repository.RegisterNumberRepository;
import sideproject.puddy.security.util.SecurityUtil;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class DogService {
    private final DogRepository dogRepository;
    private final DogTagService dogTagService;
    private final DogTypeService dogTypeService;
    private final DogTagMapRepository dogTagMapRepository;
    private final AuthService authService;
    private final DogTransactionalService dogTransactionalService;
    private final RegisterNumberRepository registerNumberRepository;

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
        Dog dog = findByPersonAndId(id);
        dog.updateDog(request.getImage(), dogTypeService.findByContent(request.getType()), request.getGender(), request.isNeuter());
        dogTagMapRepository.deleteAll(dogTagMapRepository.findAllByDog(dog));
        request.getTags().forEach(dogTag -> dogTagMapRepository.save(new DogTagMap(dog, dogTagService.findByContent(dogTag.getContent()))));
        return ResponseEntity.ok().body("ok");
    }
    @Transactional
    public ResponseEntity<String> deleteDog(Long id){
        Dog dog = findByPersonAndId(id);
        dogRepository.delete(dog);
        return ResponseEntity.ok().body("ok");
    }
    public DogDetailResponse findDog(Long id){
        Dog dog = findByPersonAndId(id);
        List<TagDto> tags = dog.getDogTagMaps().stream().map(dogTagMap -> new TagDto(dogTagMap.getDogTag().getContent())).toList();
        return new DogDetailResponse(dog.getImage(), dog.getGender(), dog.getDogType().getContent(), dog.isNeuter(), tags);
    }
    public boolean existRegisterNum(Long registerNum){
        return registerNumberRepository.existsByRegisterNum(registerNum);
    }
    public Dog findByPersonAndId(Long id){
        Person person = authService.findById(SecurityUtil.getCurrentUserId());
        return dogRepository.findByPersonAndId(person, id).orElseThrow(() -> new CustomException(ErrorCode.DOG_NUM_NOT_FOUND));
    }
    public Dog findByPersonAndMain(Person person){
        return dogRepository.findByPersonAndMain(person, true).orElseThrow(() -> new CustomException(ErrorCode.DOG_NUM_NOT_FOUND));
    }
}
