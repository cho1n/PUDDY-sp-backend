package sideproject.puddy.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sideproject.puddy.dto.token.TokenDto;
import sideproject.puddy.exception.CustomException;
import sideproject.puddy.exception.ErrorCode;
import sideproject.puddy.model.Person;
import sideproject.puddy.model.RefreshToken;
import sideproject.puddy.repository.redis.RefreshTokenRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    public RefreshToken findByPersonId(Long personId){
        return refreshTokenRepository.findByPersonId(personId).orElseThrow(() -> new CustomException(ErrorCode.INVALID_REFRESH_TOKEN));
    }
    @Transactional
    public ResponseEntity<String> saveToken(TokenDto tokenDto, Person person) {
        if (refreshTokenRepository.existsByPersonId(person.getId())){
            throw new CustomException(ErrorCode.INVALID_LOGIN);
        }
        refreshTokenRepository.save(new RefreshToken(tokenDto.getRefreshToken(), person.getId()));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", tokenDto.getGrantType() + " " + tokenDto.getAccessToken());
        httpHeaders.add("ReAuthorization", tokenDto.getGrantType() + " " + tokenDto.getRefreshToken());
        return ResponseEntity.ok().headers(httpHeaders).body("ok");
    }
    @Transactional
    public ResponseEntity<String> updateToken(TokenDto tokenDto, Person person){
        RefreshToken refreshToken = findByPersonId(person.getId());
        refreshToken.updateToken(tokenDto.getRefreshToken());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", tokenDto.getGrantType() + " " + tokenDto.getAccessToken());
        httpHeaders.add("ReAuthorization", tokenDto.getGrantType() + " " + tokenDto.getRefreshToken());
        return ResponseEntity.ok().headers(httpHeaders).body("ok");
    }
    @Transactional
    public ResponseEntity<String> deleteRefreshToken(Long personId){
        refreshTokenRepository.delete(findByPersonId(personId));
        return ResponseEntity.ok().body("ok");
    }
}
