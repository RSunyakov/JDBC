package ru.springuser.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import ru.springuser.dto.SignUpDto;
import ru.springuser.model.Role;
import ru.springuser.model.State;
import ru.springuser.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.springuser.repositories.UsersRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.UUID;

@Service
public class SignUpServiceImpl implements SignUpService {
    private static String URL = "http://localhost:8080";

    @Autowired
    MailService mailService;

    @Autowired
    UsersRepository repository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void signUp(SignUpDto form) {
        User user = User.builder()
                .email(form.getEmail())
                .activationCode(UUID.randomUUID().toString())
                .hashPassword(passwordEncoder.encode(form.getPassword()))
                .country(form.getCountry())
                .gender(form.getGender())
                .birthday(form.getBirthday())
                .state(State.NOT_CONFIRMED)
                .role(Role.USER)
                .build();
        repository.save(user);
    }


    public boolean activateUser(String code) {
        Optional<User> optionalUser = repository.findByCode(code);
        if (!optionalUser.isPresent()) {
            return false;
        }
        optionalUser.get().setActivationCode(null);
        repository.updateCode(optionalUser.get().getId(), optionalUser.get().getActivationCode());
        return true;
    }
}
