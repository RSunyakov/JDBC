package ru.kpfu.itis.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.dto.SignUpDto;
import ru.kpfu.itis.models.Role;
import ru.kpfu.itis.models.State;
import ru.kpfu.itis.models.User;
import ru.kpfu.itis.repositories.users.UsersRepository;
@Service
public class SignUpServiceImpl implements SignUpService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UsersRepository repository;

    @Override
    public void SignUp(SignUpDto form) {
        User user = User.builder()
                .email(form.getEmail())
                .hashPassword(passwordEncoder.encode(form.getPassword()))
                .firstName(form.getFirstName())
                .lastName(form.getLastName())
                .auditoriumList(form.getAuditoriumList())
                .gender(form.getGender())
                .typeOfStudent(form.getTypeOfStudent())
                .state(State.NOT_CONFIRMED)
                .role(Role.USER)
                .build();
        repository.save(user);
    }
}
