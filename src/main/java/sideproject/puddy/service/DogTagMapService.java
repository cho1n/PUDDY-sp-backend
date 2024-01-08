package sideproject.puddy.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sideproject.puddy.dto.tag.TagDto;
import sideproject.puddy.model.Dog;
import sideproject.puddy.model.DogTagMap;
import sideproject.puddy.repository.DogTagMapRepository;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class DogTagMapService {
    private final DogTagMapRepository dogTagMapRepository;
    private final DogTagService dogTagService;
    @Transactional
    public void deleteAllTags(Dog dog){
        dogTagMapRepository.deleteAll(dogTagMapRepository.findAllByDog(dog));
    }
    @Transactional
    public void saveAllTags(List<TagDto> tags, Dog dog){
        tags.forEach(dogTag -> dogTagMapRepository.save(new DogTagMap(dog, dogTagService.findByContent(dogTag.getContent()))));
    }
}
