package sideproject.puddy.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sideproject.puddy.model.DogType;
import sideproject.puddy.repository.DogTypeRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class DogTypeService {
    private final DogTypeRepository dogTypeRepository;
    public DogType findByContent(String content){
        return dogTypeRepository.findByContent(content).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 종류입니다"));
    }
}
