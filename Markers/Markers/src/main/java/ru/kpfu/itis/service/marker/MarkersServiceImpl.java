package ru.kpfu.itis.service.marker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.dto.AuditoriumDto;
import ru.kpfu.itis.dto.MarkerDto;
import ru.kpfu.itis.models.Auditorium;
import ru.kpfu.itis.models.Marker;
import ru.kpfu.itis.repositories.markers.MarkersRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MarkersServiceImpl implements MarkersService {
    @Autowired
    MarkersRepository markersRepository;

    @Override
    public List<MarkerDto> getAllMarkers() {
        return MarkerDto.from(markersRepository.findAll());
    }

    @Override
    public void deleteMarker(Long markerId) {
        markersRepository.delete(markerId);
    }

    @Override
    public MarkerDto getMarker(Long markerId) {
        Optional<Marker> optionalMarker = markersRepository.find(markerId);
        if (optionalMarker.isPresent()) {
            return MarkerDto.from(optionalMarker.get());
        } else {
            throw new IllegalArgumentException("Wrong id for marker");
        }
    }
}
