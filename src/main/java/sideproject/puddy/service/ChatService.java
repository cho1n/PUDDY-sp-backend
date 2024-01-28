package sideproject.puddy.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sideproject.puddy.dto.chat.response.ChatDogDto;
import sideproject.puddy.dto.chat.response.ChatDto;
import sideproject.puddy.dto.chat.response.GetChatListResonse;
import sideproject.puddy.model.Person;
import sideproject.puddy.repository.ChatRepository;
import sideproject.puddy.security.util.SecurityUtil;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatService {
    private final AuthService authService;
    private final ChatRepository chatRepository;
    private final DogService dogService;
    public GetChatListResonse getChatList(){
        Person person = authService.findById(SecurityUtil.getCurrentUserId());
        List<ChatDto> chatList = chatRepository.findAllByFirstPersonOrSecondPerson(person, person).stream().map(chat ->
                        (new ChatDto(
                                chat.getId(),
                                chat.getFirstPerson().getId(),
                                chat.getFirstPerson().isGender(),
                                new ChatDogDto(
                                        dogService.findByPersonAndMain(chat.getFirstPerson()).getName(),
                                        dogService.findByPersonAndMain(chat.getFirstPerson()).getImage()
                                )))).toList();
        return new GetChatListResonse(chatList);
    }
}
