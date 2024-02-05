package sideproject.puddy.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sideproject.puddy.dto.token.TokenDto;
import sideproject.puddy.exception.CustomException;
import sideproject.puddy.exception.ErrorCode;
import sideproject.puddy.model.Person;
import sideproject.puddy.repository.RefreshTokenRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    public String findByPersonId(Long personId){
        String token = refreshTokenRepository.findByPersonId(personId);
        if (token == null){
            throw new CustomException(ErrorCode.INVALID_REFRESH_TOKEN);
        }
        return token;
    }
    public ResponseEntity<String> saveToken(TokenDto tokenDto, Person person) {
        if (refreshTokenRepository.existsByPersonId(person.getId())){
            throw new CustomException(ErrorCode.INVALID_LOGIN);
        }
        refreshTokenRepository.save(tokenDto.getRefreshToken(), person.getId());
        return getTokenHeader(tokenDto);
    }
    public ResponseEntity<String> updateToken(TokenDto tokenDto, Person person){
        if (!refreshTokenRepository.existsByPersonId(person.getId())){
            throw new CustomException(ErrorCode.INVALID_REFRESH_TOKEN);
        }
        refreshTokenRepository.update(tokenDto.getRefreshToken(), person.getId());
        return getTokenHeader(tokenDto);
    }

    private ResponseEntity<String> getTokenHeader(TokenDto tokenDto) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", tokenDto.getGrantType() + " " + tokenDto.getAccessToken());
        httpHeaders.add("ReAuthorization", tokenDto.getGrantType() + " " + tokenDto.getRefreshToken());
        return ResponseEntity.ok().headers(httpHeaders).body("ok");
    }

    public ResponseEntity<String> deleteRefreshToken(Long personId){
        refreshTokenRepository.delete(personId);
        return ResponseEntity.ok().body("ok");
    }

}
