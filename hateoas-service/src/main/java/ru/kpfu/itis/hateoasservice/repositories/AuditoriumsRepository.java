package ru.kpfu.itis.hateoasservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kpfu.itis.hateoasservice.models.Auditorium;

public interface AuditoriumsRepository extends JpaRepository<Auditorium, Long> {
}
