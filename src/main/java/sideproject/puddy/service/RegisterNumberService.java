package sideproject.puddy.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sideproject.puddy.repository.jpa.RegisterNumberRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class RegisterNumberService {
    private final RegisterNumberRepository registerNumberRepository;
    public boolean existRegisterNum(Long registerNum){
        return registerNumberRepository.existsByRegisterNum(registerNum);
    }
}
