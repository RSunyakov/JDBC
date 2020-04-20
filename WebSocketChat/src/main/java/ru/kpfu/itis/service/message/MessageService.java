package ru.kpfu.itis.service.message;

import ru.kpfu.itis.dto.MessageDto;
import ru.kpfu.itis.dto.UserDto;

import java.util.List;

public interface MessageService {
    void save(MessageDto messageDto);
    MessageDto find(Long messageId);
    List<MessageDto> findAll();
    List<MessageDto> findByUserId(Long userId);
    List<MessageDto> findByRoomId(Long roomId);
}
