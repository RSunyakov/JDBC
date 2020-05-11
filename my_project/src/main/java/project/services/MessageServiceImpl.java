package project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.models.Message;
import project.repositories.MessageRepository;

import java.util.List;

@Component
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageRepository messageRepository;

    @Override
    public Long save(Message message) {
        return messageRepository.save(message);
    }

    @Override
    public List<Message> findAllForUser(Long userId) {
        return messageRepository.findAllForUser(userId);
    }

    @Override
    public void makeAllMessagesFromUserDelivered(Long userId) {
        messageRepository.makeAllMessagesFromUserDelivered(userId);
    }

    @Override
    public List<Message> getAllNotDeliveredForUser(Long userId) {
        return messageRepository.getAllNotDeliveredForUser(userId);
    }

    @Override
    public List<Long> getUsers() {
        return messageRepository.getUsers();
    }

    @Override
    public List<Message> getAllNotDeliveredFromUser(Long userId) {
        return messageRepository.getAllNotDeliveredFromUser(userId);
    }

    @Override
    public void makeAllMessagesToUserDelivered(Long userId) {
        messageRepository.makeAllMessagesToUserDelivered(userId);
    }
}
