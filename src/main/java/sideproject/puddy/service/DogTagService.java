package sideproject.puddy.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sideproject.puddy.model.DogTag;
import sideproject.puddy.repository.DogTagRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class DogTagService {
    private final DogTagRepository dogTagRepository;
    public DogTag findByContent(String content){
        return dogTagRepository.findByContent(content).orElseThrow(() -> new IllegalArgumentException("태그가 없습니다"));
    }
}
