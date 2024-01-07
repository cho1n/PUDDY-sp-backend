package sideproject.puddy.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import sideproject.puddy.dto.kakao.Coordinates;
import sideproject.puddy.dto.person.request.UpdatePersonRequest;
import sideproject.puddy.dto.person.response.PersonInfoResponse;
import sideproject.puddy.model.Person;
import sideproject.puddy.repository.PersonRepository;
import sideproject.puddy.security.util.SecurityUtil;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonService {

    private final AuthService authService;
    private final PersonRepository personRepository;
    private final KakaoMapService kakaoMapService;
    private final BCryptPasswordEncoder encoder;

    public PersonInfoResponse findPersonInfo() {
        Person person = authService.findById(SecurityUtil.getCurrentUserId());
        return PersonInfoResponse.of(
                person.getLogin(),
                person.getBirth(),
                person.getMainAddress(),
                person.getSubAddress(),
                person.isGender()
        );
    }

    @Transactional
    public ResponseEntity<String> updatePerson(UpdatePersonRequest updatePersonRequest) {
        Person person = authService.findById(SecurityUtil.getCurrentUserId());
        Coordinates coordinates = kakaoMapService.getCoordinate(updatePersonRequest.getMainAddress());
        String password = (updatePersonRequest.getPassword() != null) ?
                encoder.encode(updatePersonRequest.getPassword()) : person.getPassword();
        person.updatePerson(
                password,
                updatePersonRequest.getMainAddress(),
                updatePersonRequest.getSubAddress(),
                updatePersonRequest.getBirth(),
                updatePersonRequest.isGender(),
                coordinates.getLat(),
                coordinates.getLng()
        );
        return ResponseEntity.ok().body("ok");
    }

    @Transactional
    public ResponseEntity<String> deletePerson() {
        Person person = authService.findById(SecurityUtil.getCurrentUserId());
        personRepository.delete(person);
        return ResponseEntity.ok().body("ok");
    }

}
