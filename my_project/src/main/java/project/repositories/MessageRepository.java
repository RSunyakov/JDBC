package project.repositories;

import project.models.Message;

import java.util.List;

public interface MessageRepository extends CrudRepository<Long, Message> {

    List<Message> findAllForUser(Long userId);

    void makeAllMessagesFromUserDelivered(Long userId);

    List<Message> getAllNotDeliveredForUser(Long userId);

    List<Long> getUsers();

    List<Message> getAllNotDeliveredFromUser(Long userId);

    void makeAllMessagesToUserDelivered(Long userId);
}
