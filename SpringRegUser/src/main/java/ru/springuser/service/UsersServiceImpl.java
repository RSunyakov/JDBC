package ru.springuser.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.springuser.dto.SignUpDto;
import ru.springuser.dto.UserDto;
import ru.springuser.exception.NotFoundException;
import ru.springuser.model.User;
import ru.springuser.repositories.UsersRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsersServiceImpl implements UsersService {

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public List<UserDto> getAllUsers() {
        return UserDto.from(usersRepository.findAll());
    }

    @Override
    public UserDto getUser(Long userId) {
        Optional<User> optionalUser = usersRepository.find(userId);
        if (optionalUser.isPresent()) {
            return UserDto.from(optionalUser.get());
        } else {
            return UserDto.from(Optional.ofNullable(new User()).get());
        }
    }

    @Override
    public UserDto addUser(SignUpDto userData) {
        User user = User.builder()
                .email(userData.getEmail())
                .activationCode(UUID.randomUUID().toString())
                .hashPassword(userData.getPassword())
                .gender(userData.getGender())
                .country(userData.getCountry())
                .birthday(userData.getBirthday())
                .build();
        usersRepository.save(user);
        return UserDto.from(user);
    }

}
