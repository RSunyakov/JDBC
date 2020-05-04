package ru.kpfu.itis.javalabmessagequeue.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kpfu.itis.javalabmessagequeue.models.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Task findByQueueName(String queueName);
}
