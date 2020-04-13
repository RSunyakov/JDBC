package ru.kpfu.itis.repositories.message;

import ru.kpfu.itis.models.Message;
import ru.kpfu.itis.repositories.CrudRepository;

import java.util.List;

public interface MessagesRepository extends CrudRepository<Long, Message> {
    void update(Message message);
    List<Message> findByUserId(Long userId);
}
