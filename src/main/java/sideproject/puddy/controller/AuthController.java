package sideproject.puddy.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sideproject.puddy.dto.person.request.SignInRequest;
import sideproject.puddy.dto.person.request.SignUpRequest;
import sideproject.puddy.dto.person.response.SignUpResponse;
import sideproject.puddy.service.AuthService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    @PostMapping("/signup")
    public SignUpResponse signUp(@RequestBody SignUpRequest signUpRequest){
        return authService.signUp(signUpRequest);
    }
    @PostMapping("/login")
    public ResponseEntity<String> signIn(@RequestBody SignInRequest signInRequest){
        return authService.signIn(signInRequest);
    }
    @PostMapping("/reissue")
    public ResponseEntity<String> reissue(HttpServletRequest request){
        return authService.reissue(request);
    }
    @GetMapping("/findsamelogin")
    public boolean findSameLogin(@RequestParam String login){
        return authService.findSameLogin(login);
    }
}
