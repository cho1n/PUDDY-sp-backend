package sideproject.puddy.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sideproject.puddy.dto.tag.TagDto;
import sideproject.puddy.dto.tag.TagListDto;
import sideproject.puddy.exception.CustomException;
import sideproject.puddy.exception.ErrorCode;
import sideproject.puddy.model.DogTag;
import sideproject.puddy.repository.DogTagRepository;


@Service
@Slf4j
@RequiredArgsConstructor
public class DogTagService {
    private final DogTagRepository dogTagRepository;
    public DogTag findByContent(String content){
        return dogTagRepository.findByContent(content).orElseThrow(() -> new CustomException(ErrorCode.TAG_NOT_FOUND));
    }
    public TagListDto findAll(){
        return new TagListDto(dogTagRepository.findAll().stream().map(dogTag -> new TagDto(dogTag.getContent())).toList());
    }
}
