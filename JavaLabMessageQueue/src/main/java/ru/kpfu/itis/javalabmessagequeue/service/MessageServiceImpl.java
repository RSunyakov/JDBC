package ru.kpfu.itis.javalabmessagequeue.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.javalabmessagequeue.models.Message;
import ru.kpfu.itis.javalabmessagequeue.repositories.MessageRepository;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageRepository messageRepository;

    @Override
    public void saveMessage(Message message) {
        messageRepository.save(message);
    }

    @Override
    public void updateMessage(Message message) {
      Message message1 =  messageRepository.findMessagesByMessageId(message.getMessageId());
      message1.setState(message.getState());
      messageRepository.save(message1);
    }

}
