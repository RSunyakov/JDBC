package ru.kpfu.itis.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.dto.AuditoriumDto;
import ru.kpfu.itis.service.auditorium.AuditoriumService;

import java.util.List;

@RestController
@RequestMapping(value = "/api/auditorium-management")
public class AuditoriumRestController {

    @Autowired
    private AuditoriumService auditoriumService;

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/auditoriums", method = RequestMethod.GET)
    public ResponseEntity<List<AuditoriumDto>> getAuditoriums() {
        return ResponseEntity.ok(auditoriumService.getAllAuditoriums());
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/auditoriums/{auditorium-id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteAuditorium(@PathVariable("auditorium-id") Long auditoriumId) {
        auditoriumService.deleteAuditorium(auditoriumId);
        return ResponseEntity.accepted().build();
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/auditoriums/{auditorium-id}", method = RequestMethod.GET)
    public ResponseEntity<AuditoriumDto> getAuditorium(@PathVariable("auditorium-id") Long auditoriumId) {
        return ResponseEntity.ok(auditoriumService.getAuditorium(auditoriumId));
    }
}
