package sideproject.puddy.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sideproject.puddy.dto.dog.request.PostDogRequest;
import sideproject.puddy.model.Dog;
import sideproject.puddy.model.Person;
import sideproject.puddy.repository.DogRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
@Service
@Slf4j
@RequiredArgsConstructor
public class DogTransactionalService {
    private final DogRepository dogRepository;
    private final DogTagMapService dogTagMapService;
    private final DogTypeService dogTypeService;
    @Transactional
    public void save(Person person, PostDogRequest request){
        boolean main = !dogRepository.existsByPerson(person);
        Dog dog = dogRepository.save(new Dog(request.getName(), request.isGender(), LocalDate.parse(request.getBirth(), DateTimeFormatter.ISO_DATE)
                , dogTypeService.findByContent(request.getType()), request.getRegisterNum(), request.isNeuter(), main, request.getImage(), person));
        dogTagMapService.saveAllTags(request.getTags(), dog);
    }
}
