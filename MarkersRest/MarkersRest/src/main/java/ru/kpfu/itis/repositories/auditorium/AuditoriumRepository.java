package ru.kpfu.itis.repositories.auditorium;

import ru.kpfu.itis.models.Auditorium;
import ru.kpfu.itis.repositories.CrudRepository;

import java.util.Optional;

public interface AuditoriumRepository extends CrudRepository<Long, Auditorium> {
    void update(Auditorium auditorium);
    Optional<Auditorium> findByName(String name);
}
