package ru.kpfu.itis.repositories.markers;

import ru.kpfu.itis.models.Auditorium;
import ru.kpfu.itis.models.Marker;
import ru.kpfu.itis.repositories.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface MarkersRepository extends CrudRepository<Long, Marker> {
    void update(Marker marker);
    List<Marker> findByAuditoriumId(Long auditoriumId);
}
