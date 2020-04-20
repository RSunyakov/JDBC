package ru.kpfu.itis.service.signup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.dto.SignUpDto;
import ru.kpfu.itis.models.Message;
import ru.kpfu.itis.models.User;
import ru.kpfu.itis.repositories.UserRepository;

import java.util.ArrayList;

@Service
public class SignUpServiceImpl implements SignUpService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void signUp(SignUpDto form) {
        User user = User.builder()
                .name(form.getName())
                .email(form.getEmail())
                .hashPassword(passwordEncoder.encode(form.getPassword()))
                .messages(new ArrayList<Message>()).build();
        userRepository.save(user);
    }
}
