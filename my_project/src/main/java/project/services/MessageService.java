package project.services;

import project.models.Message;

import java.util.List;
import java.util.Map;

public interface MessageService {
    Long save(Message message);
    List<Message> findAllForUser(Long userId);

    void makeAllMessagesFromUserDelivered(Long userId);

    List<Message> getAllNotDeliveredForUser(Long userId);

    List<Long> getUsers();

    List<Message> getAllNotDeliveredFromUser(Long userId);

    void makeAllMessagesToUserDelivered(Long userId);
}
