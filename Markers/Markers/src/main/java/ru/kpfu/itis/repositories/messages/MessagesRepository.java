package ru.kpfu.itis.repositories.messages;

import ru.kpfu.itis.models.Messages;
import ru.kpfu.itis.repositories.CrudRepository;

import java.util.List;

public interface MessagesRepository extends CrudRepository<Long, Messages> {

    List<Messages> findAllForUser(Long userId);

    void makeAllMessagesFromUserDelivered(Long userId);

    List<Messages> getAllNotDeliveredForUser(Long userId);

    List<Long> getUsers();

    List<Messages> getAllNotDeliveredFromUser(Long userId);

    void makeAllMessagesToUserDelivered(Long userId);
}
