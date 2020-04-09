package ru.kpfu.itis.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.dto.SignUpDto;
import ru.kpfu.itis.dto.UserDto;
import ru.kpfu.itis.models.Role;
import ru.kpfu.itis.models.State;
import ru.kpfu.itis.models.User;
import ru.kpfu.itis.repositories.users.UsersRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UsersServiceImpl implements UsersService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<UserDto> getAllUsers() {
        return UserDto.from(usersRepository.findAll());
    }

    @Override
    public void deleteUser(Long userId) {
        usersRepository.delete(userId);
    }

    @Override
    public UserDto getUser(Long userId) {
        Optional<User> optionalUser = usersRepository.find(userId);
        if (optionalUser.isPresent()) {
            return UserDto.from(optionalUser.get());
        } else {
            throw new IllegalArgumentException("Wrong id for user");
        }
    }

    @Override
    public UserDto addUser(SignUpDto userData) {
        User user = User.builder()
                .email(userData.getEmail())
                .hashPassword(passwordEncoder.encode(userData.getPassword()))
                .firstName(userData.getFirstName())
                .lastName(userData.getLastName())
                .auditoriumList(userData.getAuditoriumList())
                .gender(userData.getGender())
                .typeOfStudent(userData.getTypeOfStudent())
                .state(State.NOT_CONFIRMED)
                .role(Role.USER)
                .build();
        usersRepository.save(user);
        return UserDto.from(user);
    }
}
