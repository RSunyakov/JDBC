package ru.kpfu.itis.service.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.dto.MessageDto;
import ru.kpfu.itis.models.Message;
import ru.kpfu.itis.repositories.message.MessagesRepository;
import ru.kpfu.itis.repositories.users.UsersRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private MessagesRepository messagesRepository;

    @Override
    public List<MessageDto> getAll() {
        return MessageDto.from(messagesRepository.findAll());
    }

    @Override
    public List<MessageDto> get(Long userId) {
        return MessageDto.from(messagesRepository.findByUserId(userId));
    }

    @Override
    public void remove(List<MessageDto> messagesDto) {
        for (int i = 0; i < messagesDto.size(); i++) {
            messagesRepository.delete(messagesDto.get(i).getId());
        }
    }

    @Override
    public void save(MessageDto messageDto) {
        messagesRepository.save(Message.builder()
                .id(messageDto.getId())
                .user(usersRepository.find(messageDto.getUserId()).get())
                .text(messageDto.getText())
                .build());
    }


}
