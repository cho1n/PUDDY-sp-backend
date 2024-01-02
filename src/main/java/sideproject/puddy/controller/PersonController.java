package sideproject.puddy.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sideproject.puddy.dto.person.request.SignInRequest;
import sideproject.puddy.dto.person.request.SignUpRequest;
import sideproject.puddy.service.PersonService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PersonController {
    private final PersonService personService;
    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody SignUpRequest signUpRequest){
        return personService.signUp(signUpRequest);
    }
    @PostMapping("/login")
    public ResponseEntity<String> signIn(@RequestBody SignInRequest signInRequest){
        return personService.signIn(signInRequest);
    }
    @PostMapping("/reissue")
    public ResponseEntity<String> reissue(HttpServletRequest request){
        return personService.reissue(request);
    }
    @PostMapping("/findsamelogin")
    public ResponseEntity<String> findSameLogin(@RequestBody String login){
        return personService.findSameLogin(login);
    }
}
