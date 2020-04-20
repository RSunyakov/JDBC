package ru.kpfu.itis.service.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.dto.MessageDto;
import ru.kpfu.itis.models.Message;
import ru.kpfu.itis.repositories.MessageRepository;
import ru.kpfu.itis.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MessageServiceImpl implements MessageService{
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserRepository userRepository;


    @Override
    public void save(MessageDto messageDto) {
        Message message = Message.builder()
                .roomId(messageDto.getRoomId())
                .text(messageDto.getText())
                .user(userRepository.findById(messageDto.getUserId()).get())
                .build();
        messageRepository.save(message);
    }

    @Override
    public MessageDto find(Long messageId) {
        Optional<Message> optionalMessage = messageRepository.findById(messageId);
        if (optionalMessage.isPresent()) {
            return MessageDto.from(optionalMessage.get());
        } else {
            throw new IllegalArgumentException("Wrong id for message");
        }
    }

    @Override
    public List<MessageDto> findAll() {
        return MessageDto.from(messageRepository.findAll());
    }

    @Override
    public List<MessageDto> findByUserId(Long userId) {
        return MessageDto.from(messageRepository.findAllByUserId(userId));
    }

    @Override
    public List<MessageDto> findByRoomId(Long roomId) {
      return MessageDto.from(messageRepository.findAllByRoomId(roomId));
    }
}
