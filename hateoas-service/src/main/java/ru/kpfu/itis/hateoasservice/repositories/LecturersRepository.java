package ru.kpfu.itis.hateoasservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kpfu.itis.hateoasservice.models.Lecturer;

public interface LecturersRepository extends JpaRepository<Lecturer, Long> {
}
