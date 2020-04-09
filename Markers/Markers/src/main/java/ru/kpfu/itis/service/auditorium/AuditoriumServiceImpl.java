package ru.kpfu.itis.service.auditorium;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.dto.AuditoriumDto;
import ru.kpfu.itis.models.Auditorium;
import ru.kpfu.itis.repositories.auditorium.AuditoriumRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AuditoriumServiceImpl implements AuditoriumService{
    @Autowired
    AuditoriumRepository repository;


    @Override
    public Auditorium getAuditorium(String name) {
        Optional<Auditorium> optionalAuditorium = repository.findByName(name);
        if (optionalAuditorium.isPresent()) {
            return optionalAuditorium.get();
        } else {
            throw new IllegalArgumentException("Wrong name for auditorium");
        }
    }

    @Override
    public List<AuditoriumDto> getAllAuditoriums() {
        return AuditoriumDto.from(repository.findAll());
    }

    @Override
    public void deleteAuditorium(Long auditoriumId) {
        repository.delete(auditoriumId);
    }

    @Override
    public AuditoriumDto getAuditorium(Long auditoriumId) {
        Optional<Auditorium> optionalAuditorium = repository.find(auditoriumId);
        if (optionalAuditorium.isPresent()) {
            return AuditoriumDto.from(optionalAuditorium.get());
        } else {
            throw new IllegalArgumentException("Wrong id for auditorium");
        }
    }
}
