package ru.kpfu.itis.service.user;

import ru.kpfu.itis.dto.SignUpDto;
import ru.kpfu.itis.dto.UserDto;

import java.util.List;

public interface UsersService {
    List<UserDto> getAllUsers();
    void deleteUser(Long userId);
    public UserDto getUser(Long userId);
    public UserDto addUser(SignUpDto userData);
}
