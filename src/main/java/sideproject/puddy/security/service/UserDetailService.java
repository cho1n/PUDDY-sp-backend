package sideproject.puddy.security.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sideproject.puddy.exception.CustomException;
import sideproject.puddy.exception.ErrorCode;
import sideproject.puddy.model.Person;
import sideproject.puddy.repository.PersonRepository;
import sideproject.puddy.security.CustomUserDetails;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {
    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        Person person = personRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        return CustomUserDetails.builder().username(person.getId().toString()).password(passwordEncoder.encode(person.getPassword())).build();
    }
}
