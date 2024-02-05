package sideproject.puddy.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import sideproject.puddy.dto.kakao.Coordinates;
import sideproject.puddy.dto.person.request.SignInRequest;
import sideproject.puddy.dto.person.request.SignUpRequest;
import sideproject.puddy.dto.person.response.SignUpResponse;
import sideproject.puddy.dto.token.TokenDto;
import sideproject.puddy.exception.CustomException;
import sideproject.puddy.exception.ErrorCode;
import sideproject.puddy.model.Person;
import sideproject.puddy.repository.PersonRepository;
import sideproject.puddy.security.jwt.JwtTokenProvider;
import sideproject.puddy.security.util.SecurityUtil;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final BCryptPasswordEncoder encoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final PersonRepository personRepository;
    private final KakaoMapService kakaoMapService;
    private final RefreshTokenService refreshTokenService;
    public boolean findSameLogin(String login){
        return personRepository.existsByLogin(login);
    }
    @Transactional
    public SignUpResponse signUp(SignUpRequest signUpRequest){
        Pattern pattern = Pattern.compile("^[A-Za-z0-9]{8,20}$");
        if (findSameLogin(signUpRequest.getLogin()) ||
                !kakaoMapService.isValidMainAddress(signUpRequest.getMainAddress()) ||
                        !pattern.matcher(signUpRequest.getPassword()).find()){
            throw new CustomException(ErrorCode.INVALID_SIGNUP);
        }
        String encodedPassword = encoder.encode(signUpRequest.getPassword());
        Coordinates coordinates = kakaoMapService.getCoordinate(signUpRequest.getMainAddress());
        Person person = personRepository.save(new Person(signUpRequest.getLogin(), encodedPassword,
                signUpRequest.getMainAddress(), signUpRequest.getSubAddress(),
                LocalDate.parse(signUpRequest.getBirth(), DateTimeFormatter.ISO_DATE), signUpRequest.isGender(),
                coordinates.getLat(), coordinates.getLng()));
        return new SignUpResponse(person.getId());
    }
    @Transactional
    public ResponseEntity<String> signIn(SignInRequest signInRequest){
        String encodedPassword = findByLogin(signInRequest.getLogin()).getPassword();
        if (!encoder.matches(signInRequest.getPassword(), encodedPassword)){
            throw new CustomException(ErrorCode.WRONG_LOGIN_INPUT);
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                findByLogin(signInRequest.getLogin()).getId(), encodedPassword);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        TokenDto tokenDto = jwtTokenProvider.generateToken(authentication);
        Person person = findById(Long.valueOf(authentication.getName()));
        return refreshTokenService.saveToken(tokenDto, person);
    }
    @Transactional
    public ResponseEntity<String> reissue(HttpServletRequest request) {
        String refreshToken = jwtTokenProvider.resolveRefreshToken(request);
        if (!jwtTokenProvider.validateToken(refreshToken)){
            throw new CustomException(ErrorCode.INVALID_REFRESH_TOKEN);
        }
        Authentication authentication = jwtTokenProvider.getAuthentication(refreshToken);
        Person person = findById(Long.valueOf(authentication.getName()));
        String token = refreshTokenService.findByPersonId(person.getId());
        if (!token.equals(refreshToken)){
            throw new CustomException(ErrorCode.INVALID_REFRESH_TOKEN);
        }
        TokenDto tokens = jwtTokenProvider.generateToken(authentication);
        return refreshTokenService.updateToken(tokens, person);
    }
    public ResponseEntity<String> logOut(){
        Person person = findById(SecurityUtil.getCurrentUserId());
        return refreshTokenService.deleteRefreshToken(person.getId());
    }
    public Person findById(Long id){
        return personRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }
    public Person findByLogin(String login){
        return personRepository.findByLogin(login).orElseThrow(() -> new CustomException(ErrorCode.WRONG_LOGIN_INPUT));
    }
}
