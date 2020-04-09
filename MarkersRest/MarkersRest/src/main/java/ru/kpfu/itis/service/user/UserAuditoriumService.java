package ru.kpfu.itis.service.user;

import org.springframework.security.core.Authentication;
import ru.kpfu.itis.models.Auditorium;

public interface UserAuditoriumService {
    void add(Authentication authentication, Auditorium auditorium);
}
