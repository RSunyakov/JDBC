package ru.kpfu.itis.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.dto.MarkerDto;
import ru.kpfu.itis.service.marker.MarkersService;

import java.util.List;

@RestController
@RequestMapping(value = "/api/markers-management")
public class MarkerRestController {
    @Autowired
    private MarkersService markersService;

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/markers", method = RequestMethod.GET)
    public ResponseEntity<List<MarkerDto>> getMarkers() {
        return ResponseEntity.ok(markersService.getAllMarkers());
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/markers/{marker-id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteMarker(@PathVariable("marker-id") Long markerId) {
        markersService.deleteMarker(markerId);
        return ResponseEntity.accepted().build();
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/markers/{marker-id}", method = RequestMethod.GET)
    public ResponseEntity<MarkerDto> getMarker(@PathVariable("marker-id") Long markerId) {
        return ResponseEntity.ok(markersService.getMarker(markerId));
    }


}
