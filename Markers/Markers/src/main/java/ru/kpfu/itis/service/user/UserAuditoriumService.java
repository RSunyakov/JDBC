package ru.kpfu.itis.service.user;

import org.springframework.security.core.Authentication;
import ru.kpfu.itis.models.Auditorium;
import ru.kpfu.itis.models.User;

public interface UserAuditoriumService {
    void add(User user, Auditorium auditorium);
}
