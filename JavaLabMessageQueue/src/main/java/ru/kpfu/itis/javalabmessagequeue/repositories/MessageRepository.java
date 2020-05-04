package ru.kpfu.itis.javalabmessagequeue.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kpfu.itis.javalabmessagequeue.models.Message;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findMessagesByState(String state);
    Message findMessagesByMessageId(String messageId);
}
