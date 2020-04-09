package ru.kpfu.itis.service.marker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.models.Auditorium;
import ru.kpfu.itis.models.Marker;
import ru.kpfu.itis.repositories.markers.MarkersRepository;

import java.util.List;
import java.util.Optional;

@Service
public class EditMarkerServiceImpl implements EditMarkerService {

    @Autowired
    MarkersRepository markersRepository;

    @Override
    public Marker getMarker(Long id) {
       Optional<Marker> markerOptional = markersRepository.find(id);
       if (markerOptional.isPresent()) {
           return markerOptional.get();
       } else {
           throw new IllegalArgumentException("Wrong id for marker");
       }
    }

    @Override
    public List<Marker> getMarkers(Long auditoriumId) {
        return markersRepository.findByAuditoriumId(auditoriumId);
    }

    @Override
    public void addMarkerToAuditorium(Marker marker, Auditorium auditorium) {
        marker.setAuditorium(auditorium);
        marker.setCondition(100);
        markersRepository.save(marker);
    }

    @Override
    public void editMarkerCondition(Marker marker) {
        marker.setCondition(marker.getCondition() - 10);
        markersRepository.update(marker);
    }
}
