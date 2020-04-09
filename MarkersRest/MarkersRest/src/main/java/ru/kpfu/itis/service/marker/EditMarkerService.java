package ru.kpfu.itis.service.marker;

import ru.kpfu.itis.models.Auditorium;
import ru.kpfu.itis.models.Marker;

import java.util.List;

public interface EditMarkerService {
    Marker getMarker(Long id);
    List<Marker> getMarkers(Long auditoriumId);
    void addMarkerToAuditorium(Marker marker, Auditorium auditorium);
    void editMarkerCondition(Marker marker);
}
