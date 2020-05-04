package ru.kpfu.itis.javalabmessagequeue.service;

import ru.kpfu.itis.javalabmessagequeue.models.Task;

public interface TaskService {
    void saveTask(Task task);
    void updateTask(String queueName, String state);
    Task getTask(String queueName);
}
