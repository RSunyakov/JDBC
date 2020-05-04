package ru.kpfu.itis.javalabmessagequeue.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.javalabmessagequeue.models.Task;
import ru.kpfu.itis.javalabmessagequeue.repositories.TaskRepository;


@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Override
    public void saveTask(Task task) {
        taskRepository.save(task);
    }

    @Override
    public void updateTask(String queueName, String state) {
        Task task = taskRepository.findByQueueName(queueName);
        task.setState(state);
        taskRepository.save(task);
    }

    @Override
    public Task getTask(String queueName) {
        return taskRepository.findByQueueName(queueName);
    }
}
