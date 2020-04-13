package ru.kpfu.itis.service.message;

import ru.kpfu.itis.dto.MessageDto;

import java.util.List;

public interface ChatService {
    List<MessageDto> getAll();
    List<MessageDto> get(Long userId);
    void remove(List<MessageDto> messageDto);
    void save(MessageDto messageDto);
}
