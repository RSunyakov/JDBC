package ru.kpfu.itis.service.auditorium;

import ru.kpfu.itis.dto.AuditoriumDto;
import ru.kpfu.itis.models.Auditorium;

import java.util.List;

public interface AuditoriumService {
    Auditorium getAuditorium(String name); //TODO:Переделать на AuditoriumDto
    List<AuditoriumDto> getAllAuditoriums();
    void deleteAuditorium(Long auditoriumId);
    public AuditoriumDto getAuditorium(Long auditoriumId);
    List<String> getAllNamesOfAuditoriums();
}
