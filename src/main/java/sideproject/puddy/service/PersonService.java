package sideproject.puddy.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sideproject.puddy.dto.person.response.PersonInfoResponse;
import sideproject.puddy.model.Person;
import sideproject.puddy.security.util.SecurityUtil;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonService {

    private final AuthService authService;

    public PersonInfoResponse findPersonInfo() {
        Person person = authService.findById(SecurityUtil.getCurrentUserId());
        return PersonInfoResponse.of(
                person.getLogin(),
                person.getPassword(),
                person.getBirth(),
                person.getMainAddress(),
                person.getSubAddress(),
                person.isGender()
        );
    }

}
