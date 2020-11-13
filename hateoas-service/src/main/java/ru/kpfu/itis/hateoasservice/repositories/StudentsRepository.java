package ru.kpfu.itis.hateoasservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kpfu.itis.hateoasservice.models.Student;


public interface StudentsRepository extends JpaRepository<Student, Long> {
}