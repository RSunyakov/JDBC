package ru.kpfu.itis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kpfu.itis.models.Message;
import ru.kpfu.itis.models.User;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllByUserId(Long userId);
    List<Message> findAllByRoomId(Long roomId);
}
