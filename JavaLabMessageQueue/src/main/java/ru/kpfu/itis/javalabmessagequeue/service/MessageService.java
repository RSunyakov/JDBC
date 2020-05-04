package ru.kpfu.itis.javalabmessagequeue.service;

import ru.kpfu.itis.javalabmessagequeue.models.Message;

public interface MessageService {
    void saveMessage(Message message);
    void updateMessage(Message message);
}
