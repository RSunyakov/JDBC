package ru.kpfu.itis.hateoasservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kpfu.itis.hateoasservice.models.Lesson;


public interface LessonsRepository extends JpaRepository<Lesson, Long> {
}