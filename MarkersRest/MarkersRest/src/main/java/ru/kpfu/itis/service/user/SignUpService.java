package ru.kpfu.itis.service.user;

import org.springframework.stereotype.Service;
import ru.kpfu.itis.dto.SignUpDto;

@Service
public interface SignUpService {
    void SignUp(SignUpDto form);
}
