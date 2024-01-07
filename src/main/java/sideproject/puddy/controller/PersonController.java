package sideproject.puddy.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sideproject.puddy.dto.person.response.PersonInfoResponse;
import sideproject.puddy.service.PersonService;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PersonController {
    private final PersonService personService;

    @GetMapping("/person")
    public ResponseEntity<PersonInfoResponse> getPersonInfo() {
        final PersonInfoResponse myInformation = personService.findPersonInfo();
        return ResponseEntity.ok(myInformation);
    }

}
