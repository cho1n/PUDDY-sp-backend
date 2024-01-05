package sideproject.puddy.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import sideproject.puddy.dto.kakao.Coordinates;
import sideproject.puddy.dto.person.request.SignInRequest;
import sideproject.puddy.dto.person.request.SignUpRequest;
import sideproject.puddy.dto.token.TokenDto;
import sideproject.puddy.model.Person;
import sideproject.puddy.repository.PersonRepository;
import sideproject.puddy.security.jwt.JwtTokenProvider;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final BCryptPasswordEncoder encoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final PersonRepository personRepository;
    private final KakaoMapService kakaoMapService;
    public ResponseEntity<String> findSameLogin(String login){
        if (findByLogin(login) != null){
            throw new RuntimeException("이미 존재하는 아이디 입니다.");
        }
        return ResponseEntity.ok().body("ok");
    }
    @Transactional
    public ResponseEntity<String> signUp(SignUpRequest signUpRequest){
        String encodedPassword = encoder.encode(signUpRequest.getPassword());
        Coordinates coordinates = kakaoMapService.getCoordinate(signUpRequest.getMainAddress());
        personRepository.save(new Person(signUpRequest.getLogin(), encodedPassword, signUpRequest.getMainAddress(), signUpRequest.getSubAddress(), signUpRequest.getBirth(), signUpRequest.getGender(), coordinates.getLat(), coordinates.getLng()));
        return ResponseEntity.ok().body("ok");
    }
    private ResponseEntity<String> getStringResponseEntity(TokenDto tokenDto, Person person) {
        person.updateToken(tokenDto.getRefreshToken());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", tokenDto.getGrantType() + " " + tokenDto.getAccessToken());
        httpHeaders.add("ReAuthorization", tokenDto.getGrantType() + " " + tokenDto.getRefreshToken());
        return ResponseEntity.ok().headers(httpHeaders).body("ok");
    }
    @Transactional
    public ResponseEntity<String> signIn(SignInRequest signInRequest){
        if (!personRepository.existsByLogin(signInRequest.getLogin())){
            throw new RuntimeException("존재하지 않는 회원입니다.");
        }
        String encodedPassword = findByLogin(signInRequest.getLogin()).getPassword();
        log.info(findByLogin(signInRequest.getLogin()).getLogin());
        if (!encoder.matches(signInRequest.getPassword(), encodedPassword)){
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(findByLogin(signInRequest.getLogin()).getId(), encodedPassword);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        TokenDto tokenDto = jwtTokenProvider.generateToken(authentication);
        Person person = findById(Long.valueOf(authentication.getName()));
        return getStringResponseEntity(tokenDto, person);
    }
    @Transactional
    public ResponseEntity<String> reissue(HttpServletRequest request) {
        String refreshToken = jwtTokenProvider.resolveRefreshToken(request);
        if (!jwtTokenProvider.validateToken(refreshToken)){
            throw new RuntimeException("Refresh Token이 유효하지 않습니다");
        }
        Authentication authentication = jwtTokenProvider.getAuthentication(refreshToken);
        Person person = findById(Long.valueOf(authentication.getName()));
        if (!person.getRefreshToken().equals(refreshToken)){
            throw new RuntimeException("Refresh Token이 유효하지 않습니다");
        }
        TokenDto tokens = jwtTokenProvider.generateToken(authentication);
        return getStringResponseEntity(tokens, person);
    }
    public Person findById(Long id){
        return personRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않습니다"));
    }
    public Person findByLogin(String login){
        return personRepository.findByLogin(login).orElseThrow(() -> new IllegalArgumentException("존재하지 않습니다"));
    }
}
