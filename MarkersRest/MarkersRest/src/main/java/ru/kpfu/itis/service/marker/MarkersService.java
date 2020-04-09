package ru.kpfu.itis.service.marker;

import ru.kpfu.itis.dto.MarkerDto;

import java.util.List;

public interface MarkersService {
    List<MarkerDto> getAllMarkers();
    void deleteMarker(Long markerId);
    public MarkerDto getMarker(Long markerId);
}
